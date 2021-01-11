package com.dnedev.photoeditor.ui.edit

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.dnedev.photoeditor.utils.EMPTY_EDIT_TEXT
import kotlin.properties.Delegates

class EditUiModel : BaseObservable() {
    @get:Bindable
    var photoUrl: String by Delegates.observable(EMPTY_EDIT_TEXT) { _, _, _ ->
        notifyPropertyChanged(BR.photoUrl)
    }
}