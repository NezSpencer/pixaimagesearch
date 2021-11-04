package com.nezspencer.pixaimagesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.nezspencer.pixaimagesearch.databinding.FragmentImageDetailBinding

class ImageDetailFragment : Fragment() {

    private var _binding: FragmentImageDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ImageDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(binding.ivImage).load(args.image.imageUrl).into(binding.ivImage)
        binding.tvUsername.text = args.image.user
        binding.tvComments.text = String.format("%,d", args.image.comments)
        binding.tvLikes.text = String.format("%,d", args.image.likes)
        binding.tvDownloads.text = String.format("%,d", args.image.downloads)
        val adapter = TagsAdapter{}
        binding.rvTags.adapter = adapter
        adapter.submitList(args.image.tags.split(", "))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}