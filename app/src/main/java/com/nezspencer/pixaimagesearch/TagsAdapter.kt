package com.nezspencer.pixaimagesearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nezspencer.pixaimagesearch.databinding.ItemTagBinding

class TagsAdapter(private val onTagClicked: (String) -> Unit) :
    ListAdapter<String, TagsAdapter.TagsHolder>(TagsDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsHolder =
        TagsHolder.create(parent)

    override fun onBindViewHolder(holder: TagsHolder, position: Int) {
        holder.bind(getItem(position), onTagClicked)
    }

    class TagsHolder private constructor(private val binding: ItemTagBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tag: String, onTagClicked: (String) -> Unit) = with(binding) {
            tvTag.text = String.format("#%s",tag)
            tvTag.blockingClickListener { onTagClicked.invoke(tag) }
        }

        companion object {
            fun create(parent: ViewGroup): TagsHolder = TagsHolder(
                ItemTagBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    class TagsDiff : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }
}