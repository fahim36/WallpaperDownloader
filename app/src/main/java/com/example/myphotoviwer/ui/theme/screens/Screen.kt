package com.example.myphotoviwer.ui.theme.screens

sealed class Screen(val route: String) {
    object PhotoViewerScreen : Screen("photo_viewer_screen")
}