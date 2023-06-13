package com.example.myphotoviwer.presentation.components

import android.app.Activity
import android.app.WallpaperColors
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.myphotoviwer.presentation.components.photolist.PhotoViewerViewModel
import com.example.myphotoviwer.ui.theme.MainBGColor
import com.example.myphotoviwer.ui.theme.shared.pxToDpHeight
import com.example.myphotoviwer.ui.theme.shared.pxToDpWidth
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto
import com.unsplash.pickerandroid.photopicker.presentation.UnsplashPickerActivity
import kotlinx.coroutines.launch


@Composable
fun PhotoListScreen(
    navController: NavHostController,
    viewModel: PhotoViewerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val wallpaperManager = WallpaperManager.getInstance(context.applicationContext)
    val activity = LocalContext.current as Activity

//    val colorsChangedListener = remember { MyColorsChangedListener(activity) }
//    wallpaperManager.addOnColorsChangedListener(colorsChangedListener,Handler(Looper.getMainLooper()))
    wallpaperManager.addOnColorsChangedListener({ _, _ ->
        activity.moveTaskToBack(true)
        viewModel.setLoading(false)
    }, Handler(Looper.getMainLooper()))
    val mStartForResult = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == ComponentActivity.RESULT_OK) {
            val data = result.data
            val photos: ArrayList<UnsplashPhoto>? = data?.getParcelableArrayListExtra(
                UnsplashPickerActivity.EXTRA_PHOTOS
            )
            viewModel.setSelectedPhotoList(photos)
            Log.d("TAG", "startPicker: $photos")
        }
    }
    val intent: Intent = UnsplashPickerActivity.getStartingIntent(context, true)
    LaunchedEffect(Unit) {
        mStartForResult.launch(intent)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBGColor)
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    horizontal = 8f.pxToDpWidth(context),
                    vertical = 16f.pxToDpHeight(context)
                ),
            columns = GridCells.Fixed(3),
            content = {
                items(state.photoList) {
                    Box(
                        modifier = Modifier
                            .height(150f.pxToDpHeight(context))
                            .width(150f.pxToDpWidth(context))
                            .clickable {
                                viewModel.setLoading(true)
                                var image: Bitmap? = null
                                scope
                                    .launch {
                                        image = viewModel.downloadImage(it.urls.regular!!)
                                    }
                                    .invokeOnCompletion {
                                        if (image != null) {
                                            wallpaperManager.setBitmap(image)
                                        }
                                    }
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "",
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 10.dp)
                        )
                        AsyncImage(
                            model = it.urls.thumb,
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }
            })
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50f.pxToDpHeight(context))
                    .align(Alignment.Center)
                    .background(Color.Transparent),
                color = Color.White,
                4.dp
            )
        }
    }
}
