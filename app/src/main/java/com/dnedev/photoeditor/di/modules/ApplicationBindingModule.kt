package com.dnedev.photoeditor.di.modules

import com.dnedev.photoeditor.ui.home.HomeFragment
import com.dnedev.photoeditor.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ApplicationBindingModule {

    @ContributesAndroidInjector
    fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    fun homeFragment(): HomeFragment
}