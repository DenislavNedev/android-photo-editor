package com.dnedev.photoeditor.di

import com.dnedev.photoeditor.PhotoEditorApplication
import com.dnedev.photoeditor.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelBuilderModule::class,
        ApplicationBindingModule::class,
    ]
)
interface AppComponent : AndroidInjector<PhotoEditorApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: PhotoEditorApplication): Builder

        fun build(): AppComponent
    }
}