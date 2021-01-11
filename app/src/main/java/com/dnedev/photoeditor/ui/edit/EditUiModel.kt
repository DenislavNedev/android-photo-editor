package com.dnedev.photoeditor.ui.edit

import android.graphics.Bitmap
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.dnedev.photoeditor.ui.edit.colors.ColorOverlay
import com.dnedev.photoeditor.utils.DEFAULT_BRIGHTNESS
import com.dnedev.photoeditor.utils.DEFAULT_CONTRAST
import com.dnedev.photoeditor.utils.EMPTY_EDIT_TEXT
import kotlin.properties.Delegates

data class EditUiModel(val colors: List<ColorOverlay>) : BaseObservable() {
    @get:Bindable
    var photoUrl: String by Delegates.observable(EMPTY_EDIT_TEXT) { _, _, _ ->
        notifyPropertyChanged(BR.photoUrl)
    }

    @get:Bindable
    var photoBrightness: Float by Delegates.observable(DEFAULT_BRIGHTNESS) { _, _, _ ->
        notifyPropertyChanged(BR.photoBrightness)
    }

    @get:Bindable
    var photoContrast: Float by Delegates.observable(DEFAULT_CONTRAST) { _, _, _ ->
        notifyPropertyChanged(BR.photoContrast)
    }

    @get:Bindable
    var photoBitmap: Bitmap? by Delegates.observable(null) { _, _, _ ->
        notifyPropertyChanged(BR.photoBitmap)
    }

    @get:Bindable
    var colorOverlay: ColorOverlay? by Delegates.observable(null) { _, _, _ ->
        notifyPropertyChanged(BR.colorOverlay)
    }
}