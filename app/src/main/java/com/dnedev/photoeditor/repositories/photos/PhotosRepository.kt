package com.dnedev.photoeditor.repositories.photos

import com.dnedev.photoeditor.di.modules.RepositoryModule
import javax.inject.Inject

class PhotosRepository @Inject constructor(
    @RepositoryModule.PhotosRemoteSourceData private val remoteSource: RemoteSource
) {
    interface RemoteSource {

        suspend fun getPhotos(
            url: String,
            headers: MutableMap<String, String>,
            photosResponseCallback: PhotosResponseCallback
        )
    }

    suspend fun getPhotos(
        url: String,
        headers: MutableMap<String, String>,
        photosResponseCallback: PhotosResponseCallback
    ) {
        remoteSource.getPhotos(
            url,
            headers,
            photosResponseCallback
        )
    }
}