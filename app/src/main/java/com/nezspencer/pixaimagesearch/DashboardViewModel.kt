package com.nezspencer.pixaimagesearch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezspencer.pixaimagesearch.data.ImageRepository
import com.nezspencer.pixaimagesearch.data.ScreenState
import com.nezspencer.pixaimagesearch.database.ImageData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    init {
        Log.e("__viewModel__", "initializing...")
    }
    private val _imageResultLiveData = MutableLiveData<ScreenState>()
    val imageResultLiveData: LiveData<ScreenState>
        get() = _imageResultLiveData

    var isDialogOpen = false
    lateinit var selectedImage: ImageData
    var showErrorDialog = false
    var showLoader = false

    fun findRelatedImages(query: String) = viewModelScope.launch {
        _imageResultLiveData.value = ScreenState.Loading
        _imageResultLiveData.value = imageRepository.fetchImages(query)
    }
}