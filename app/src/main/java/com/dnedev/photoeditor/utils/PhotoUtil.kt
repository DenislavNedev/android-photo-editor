package com.dnedev.photoeditor.utils

import android.graphics.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

object PhotoUtil {
    suspend fun getBitmapFromUrl(url: String): Bitmap = withContext(Dispatchers.IO) {
        BitmapFactory.decodeStream(URL(url).openStream())
    }

    /**
     *
     * @param bitmap input bitmap
     * @param contrast 0..10 1 is default
     * @param brightness -255..255 0 is default
     */
    fun changeBitmapContrastBrightness(
        bitmap: Bitmap,
        contrast: Float,
        brightness: Float
    ): Bitmap? = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config).let {

        Canvas(it).drawBitmap(bitmap, 0f, 0f, Paint().apply {
            colorFilter = ColorMatrixColorFilter(
                ColorMatrix(
                    floatArrayOf(
                        contrast, 0f, 0f, 0f,
                        brightness, 0f, contrast,
                        0f, 0f, brightness, 0f,
                        0f, contrast, 0f, brightness,
                        0f, 0f, 0f, 1f, 0f
                    )
                )
            )
        })
        it
    }
}