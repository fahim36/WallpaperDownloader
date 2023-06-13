package com.example.myphotoviwer.presentation.components.states

import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto

data class PhotoListState(
    val photoList: ArrayList<UnsplashPhoto>  = ArrayList(),
    val isLoading: Boolean = false
)
