package com.nezspencer.pixaimagesearch

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nezspencer.pixaimagesearch.database.ImageData
import com.nezspencer.pixaimagesearch.databinding.ItemImageBinding

class ImageAdapter(
    private val onImageClicked: (ImageData) -> Unit,
    private val onTagClicked: (String) -> Unit
) :
    ListAdapter<ImageData, ImageAdapter.ImageViewHolder>(ImageDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder.create(parent)

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position), onImageClicked, onTagClicked)
    }

    class ImageViewHolder private constructor(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            image: ImageData,
            onImageClicked: (ImageData) -> Unit,
            onTagClicked: (String) -> Unit
        ) = with(binding) {
            Glide.with(ivThumbnail).load(image.previewURL).into(ivThumbnail)
            tvUsername.text = root.context.getString(R.string.created_by_prompt, image.user)
            ivThumbnail.blockingClickListener { onImageClicked.invoke(image) }
            val tagAdapter = TagsAdapter(onTagClicked)
            rvTags.adapter = tagAdapter
            tagAdapter.submitList(image.tags.split(", "))
        }

        companion object {
            fun create(parent: ViewGroup): ImageViewHolder = ImageViewHolder(
                ItemImageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    class ImageDiff : DiffUtil.ItemCallback<ImageData>() {
        override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData): Boolean =
            oldItem.tags == newItem.tags &&
                    oldItem.comments == newItem.comments &&
                    oldItem.downloads == newItem.downloads &&
                    oldItem.imageUrl == newItem.imageUrl &&
                    oldItem.likes == newItem.likes &&
                    oldItem.previewURL == newItem.previewURL &&
                    oldItem.user == newItem.user &&
                    oldItem.userId == newItem.userId &&
                    oldItem.userImageURL == newItem.userImageURL &&
                    oldItem.associatedQueries == newItem.associatedQueries
    }
}