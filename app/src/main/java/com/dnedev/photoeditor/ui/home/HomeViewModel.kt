package com.dnedev.photoeditor.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(application: Application) : AndroidViewModel(application),
    HomePresenter {
}