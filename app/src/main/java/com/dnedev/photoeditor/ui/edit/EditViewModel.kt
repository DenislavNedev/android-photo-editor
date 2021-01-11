package com.dnedev.photoeditor.ui.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dnedev.photoeditor.ui.home.HomeUiModel
import javax.inject.Inject

class EditViewModel @Inject constructor(application: Application) : AndroidViewModel(application),
    EditPresenter {

    private val _uiModelLiveData = MutableLiveData<EditUiModel>().apply {
        value = EditUiModel()
    }
    val uiModelLiveData: LiveData<EditUiModel>
        get() = _uiModelLiveData
}