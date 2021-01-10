package com.dnedev.photoeditor

import androidx.databinding.DataBindingUtil
import com.dnedev.photoeditor.di.DaggerAppComponent
import com.dnedev.photoeditor.ui.binding.AppDataBindingComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class PhotoEditorApplication : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        DataBindingUtil.setDefaultComponent(AppDataBindingComponent())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().application(this@PhotoEditorApplication).build()
}