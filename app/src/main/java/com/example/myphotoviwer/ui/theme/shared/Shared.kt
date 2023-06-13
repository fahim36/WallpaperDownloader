package com.example.myphotoviwer.ui.theme.shared

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val TAG = "Shared"

fun Float.pxToDpWidth(context: Context): Dp {
    val widthDp =
        Resources.getSystem().displayMetrics.widthPixels / Resources.getSystem().displayMetrics.density
    return if (context.resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE)
        ((this / 600f) * widthDp).dp
    else
        ((this / 1000f) * widthDp).dp
}

fun Float.pxToDpHeight(context: Context): Dp {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val heightPixel = getWindowHeight(windowManager)
    val heightDp =
        heightPixel / Resources.getSystem().displayMetrics.density
    return if (context.resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE)
        ((this / 1000f) * heightDp).dp
    else
        ((this / 600f) * heightDp).dp

}

fun Float.pxToSp(context: Context): TextUnit {
    return pxToDpHeight(context).value.sp
}

@Suppress("DEPRECATION")
fun getWindowHeight(windowManager: WindowManager): Float {
    //For Android 11+ New Code
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        windowManager.currentWindowMetrics.bounds.height().toFloat()
    }
    //For Android <11 Deprecated Code
    else {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(metrics)
        metrics.heightPixels.toFloat()
    }
}

