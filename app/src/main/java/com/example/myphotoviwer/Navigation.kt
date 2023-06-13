package com.example.myphotoviwer

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myphotoviwer.presentation.components.PhotoListScreen
import com.example.myphotoviwer.ui.theme.screens.Screen

@Composable
fun Navigation(navController: NavController) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.PhotoViewerScreen.route
    ){
        composable(route = Screen.PhotoViewerScreen.route) {
           PhotoListScreen(navController = navController)
        }
    }
}