package com.dnedev.photoeditor.di.modules

import com.dnedev.photoeditor.repositories.photos.PhotosRemoteSource
import com.dnedev.photoeditor.repositories.photos.PhotosRepository
import com.dnedev.photoeditor.utils.RequestQueueUtil
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class PhotosRemoteSourceData

    @Singleton
    @PhotosRemoteSourceData
    @Provides
    fun providePhotosRemoteSource(requestQueueUtil: RequestQueueUtil): PhotosRepository.RemoteSource =
        PhotosRemoteSource(requestQueueUtil)
}