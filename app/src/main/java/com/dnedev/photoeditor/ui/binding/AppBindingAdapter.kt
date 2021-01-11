package com.dnedev.photoeditor.ui.binding

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.dnedev.photoeditor.R
import com.dnedev.photoeditor.ui.edit.SlidersCallback
import com.dnedev.photoeditor.ui.edit.colors.ColorOverlay
import com.dnedev.photoeditor.utils.INVALID_RESOURCE
import com.dnedev.photoeditor.utils.PhotoUtil.changeBitmapContrastBrightness
import com.dnedev.photoeditor.utils.PhotoUtil.getBitmapFromUrl
import com.google.android.material.slider.Slider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    @BindingAdapter("loadPhotoFromBitmap")
    fun loadPhotoFromBitmap(
        imageView: ImageView,
        bitmap: Bitmap?
    ) {
        bitmap?.let {
            imageView.setImageBitmap(it)
        } ?: imageView.setImageResource(R.drawable.ic_photo_not_found)
    }

    @BindingAdapter("textFromResource")
    fun visibleGone(
        view: TextView,
        @StringRes resource: Int
    ) {
        if (resource != INVALID_RESOURCE)
            view.text = view.context.getString(resource)
    }

    @BindingAdapter("updateBrightness")
    fun updateBrightness(
        slider: Slider,
        callback: SlidersCallback
    ) {
        slider.addOnChangeListener { _, value, _ ->
            callback.updateBrightness(value)
        }
    }

    @BindingAdapter("updateContrast")
    fun updateContrast(
        slider: Slider,
        callback: SlidersCallback
    ) {
        slider.addOnChangeListener { _, value, _ ->
            callback.updateContrast(value)
        }
    }

    @BindingAdapter(value = ["photoBitmap", "newContrast", "newBrightness"], requireAll = true)
    fun updateContrastAndBrightness(
        imageView: ImageView,
        photoBitmap: Bitmap?,
        newContrast: Float,
        newBrightness: Float
    ) {
        photoBitmap?.let { currentBitmap ->
            changeBitmapContrastBrightness(
                currentBitmap,
                newContrast,
                newBrightness
            )?.let { newBitmap ->
                imageView.setImageBitmap(newBitmap)
            }
        }
    }

    @BindingAdapter("changeBackgroundFromResource")
    fun changeBackgroundFromResource(
        view: View,
        @ColorRes colorId: Int
    ) {
        if (colorId != INVALID_RESOURCE) {
            view.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    view.context,
                    colorId
                )
            )
        }
    }

    @BindingAdapter("addColorOverlay")
    fun addColorOverlay(
        imageView: ImageView,
        colorOverlay: ColorOverlay?
    ) {
        colorOverlay?.let {
            imageView.colorFilter =
                PorterDuffColorFilter(
                    it.photoColorOverlay,
                    PorterDuff.Mode.OVERLAY
                )
        } ?: run { imageView.colorFilter = null }
    }
}