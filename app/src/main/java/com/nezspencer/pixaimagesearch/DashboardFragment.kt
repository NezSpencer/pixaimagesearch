package com.nezspencer.pixaimagesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.nezspencer.pixaimagesearch.data.ScreenState
import com.nezspencer.pixaimagesearch.database.ImageData
import com.nezspencer.pixaimagesearch.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val imageAdapter = ImageAdapter(::onImageClicked, ::onTagClicked)
    private val viewModel by viewModels<DashboardViewModel>()
    lateinit var confirmDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvImages.adapter = imageAdapter
        viewModel.imageResultLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Loading -> {
                    binding.progressCircular.show()
                }
                is ScreenState.SuccessState -> {
                    binding.progressCircular.hide()
                    imageAdapter.submitList(it.successData)
                }
                is ScreenState.ErrorState -> {
                    binding.progressCircular.hide()
                }
            }
        }
        binding.etSearch.addTextChangedListener {
            binding.btnSearch.isEnabled = it.toString().isNotBlank()
        }
        binding.btnSearch.blockingClickListener {
            searchImage(binding.etSearch.text.toString().trim())
        }
        if ((viewModel.imageResultLiveData.value as? ScreenState.SuccessState)?.successData.isNullOrEmpty()) {
            searchImage("fruits")
        }
    }

    private fun searchImage(query: String) {
        viewModel.findRelatedImages(query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (::confirmDialog.isInitialized && confirmDialog.isShowing) {
            confirmDialog.dismiss()
            viewModel.isDialogOpen = true
        }
        val position = (binding.rvImages.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition()
        outState.putInt(KEY_LIST_POSITION, position)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val position = savedInstanceState?.getInt(KEY_LIST_POSITION) ?: 0
        if (position <= imageAdapter.currentList.lastIndex)
        (binding.rvImages.layoutManager as GridLayoutManager).scrollToPosition(position)
        if (viewModel.isDialogOpen) {
            onImageClicked(viewModel.selectedImage)
        }
    }

    private fun onImageClicked(imageData: ImageData) {
        if (::confirmDialog.isInitialized && confirmDialog.isShowing) return
        if (!::confirmDialog.isInitialized) {
            confirmDialog = AlertDialog.Builder(requireContext())
                .setTitle("Show detail")
                .setPositiveButton("Continue"
                ) { _, _ ->
                    findNavController().navigate(
                        DashboardFragmentDirections.actionDashboardFragmentToImageDetailFragment(
                            imageData
                        )
                    )
                }
                .setNegativeButton("Cancel")
                {dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                .create()
            confirmDialog.setOnDismissListener { viewModel.isDialogOpen = false }
        }
        confirmDialog.show()
        viewModel.isDialogOpen = true
        viewModel.selectedImage = imageData
    }

    private fun onTagClicked(tag: String) {
        binding.etSearch.setText(tag)
        searchImage(tag)
    }

    companion object {
        private const val KEY_LIST_POSITION = "list_position"
    }
}