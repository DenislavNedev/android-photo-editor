package com.dnedev.photoeditor.ui.binding

import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.dnedev.photoeditor.R
import com.dnedev.photoeditor.utils.INVALID_RESOURCE
import kotlinx.coroutines.*
import java.net.URL

object AppBindingAdapter {

    @BindingAdapter("visibleGone")
    fun visibleGone(
        view: View,
        isVisible: Boolean
    ) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("loadPhotoFromUrl")
    fun loadPhotoFromUrl(
        imageView: ImageView,
        url: String
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                getBitmapFromUrl(url).let {
                    imageView.setImageBitmap(it)
                }
            } catch (exception: Exception) {
                imageView.setImageResource(R.drawable.ic_photo_not_found)
            }
        }
    }

    private suspend fun getBitmapFromUrl(url: String) = withContext(Dispatchers.IO) {
        BitmapFactory.decodeStream(URL(url).openStream())
    }

    @BindingAdapter("textFromResource")
    fun visibleGone(
        view: TextView,
        @StringRes resource: Int
    ) {
        if (resource != INVALID_RESOURCE)
            view.text = view.context.getString(resource)
    }
}