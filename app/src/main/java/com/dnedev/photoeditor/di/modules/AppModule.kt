package com.dnedev.photoeditor.di.modules

import android.app.Application
import com.dnedev.photoeditor.PhotoEditorApplication
import dagger.Module
import dagger.Provides

@Module
object AppModule {
    @Provides
    fun provideContext(application: PhotoEditorApplication): Application = application
}
