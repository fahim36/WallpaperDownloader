package com.example.myphotoviwer.presentation.components.photolist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import com.example.myphotoviwer.presentation.components.states.PhotoListState
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject


@HiltViewModel
class PhotoViewerViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(PhotoListState())
    val state = _state.asStateFlow()


    fun setSelectedPhotoList(photos: ArrayList<UnsplashPhoto>?) {
        _state.value = _state.value.copy(
            photoList = photos ?: ArrayList()
        )
    }

    suspend fun downloadImage(imgUrl: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val url = URL(imgUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun setLoading(b: Boolean) {
        _state.value = _state.value.copy(
            isLoading = b
        )
    }
}