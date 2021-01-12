package com.dnedev.photoeditor.ui.home

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.dnedev.photoeditor.utils.EMPTY_EDIT_TEXT
import kotlin.properties.Delegates
import androidx.databinding.library.baseAdapters.BR
import com.dnedev.photoeditor.R

data class HomeUiModel(val photos: List<PhotoItemUiModel>) : BaseObservable() {

    @get:Bindable
    var searchQuery: String by Delegates.observable(EMPTY_EDIT_TEXT) { _, _, _ ->
        notifyPropertyChanged(BR.searchQuery)
    }

    @get:Bindable
    var searchQueryError: String? by Delegates.observable(null) { _, _, _ ->
        notifyPropertyChanged(BR.searchQueryError)
    }

    @get:Bindable
    var infoText: Int by Delegates.observable(R.string.use_the_search_above_to_find_photos) { _, _, _ ->
        notifyPropertyChanged(BR.infoText)
    }

    @get:Bindable
    var areTherePhotos: Boolean by Delegates.observable(false) { _, _, _ ->
        notifyPropertyChanged(BR.areTherePhotos)
    }

    @get:Bindable
    var areLoadingMorePhotos: Boolean by Delegates.observable(false) { _, _, _ ->
        notifyPropertyChanged(BR.areLoadingMorePhotos)
    }
}