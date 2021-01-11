package com.dnedev.photoeditor.ui.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dnedev.photoeditor.utils.PhotoUtil.getBitmapFromUrl
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditViewModel @Inject constructor(application: Application) : AndroidViewModel(application),
    EditPresenter,
    SlidersCallback {

    private val _uiModelLiveData = MutableLiveData<EditUiModel>()
    val uiModelLiveData: LiveData<EditUiModel>
        get() = _uiModelLiveData

    fun initViewModel(url: String) {
        _uiModelLiveData.value = EditUiModel()
        _uiModelLiveData.value?.photoUrl = url
        viewModelScope.launch {
            try {
                getBitmapFromUrl(url).let {
                    _uiModelLiveData.value?.photoBitmap = it
                }
            } catch (exception: Exception) {
                _uiModelLiveData.value?.photoBitmap = null
            }
        }
    }

    override fun updateBrightness(newValue: Float) {
        _uiModelLiveData.value?.photoBrightness = newValue
    }

    override fun updateContrast(newValue: Float) {
        _uiModelLiveData.value?.photoContrast = newValue
    }
}