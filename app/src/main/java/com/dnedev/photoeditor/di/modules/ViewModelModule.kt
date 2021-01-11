package com.dnedev.photoeditor.di.modules

import androidx.lifecycle.ViewModel
import com.dnedev.photoeditor.di.ViewModelKey
import com.dnedev.photoeditor.ui.edit.EditViewModel
import com.dnedev.photoeditor.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditViewModel::class)
    abstract fun bindEditViewModel(viewModel: EditViewModel): ViewModel
}