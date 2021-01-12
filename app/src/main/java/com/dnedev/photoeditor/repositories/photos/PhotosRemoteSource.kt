package com.dnedev.photoeditor.repositories.photos

import com.dnedev.photoeditor.data.PexelsDataResponse
import com.dnedev.photoeditor.utils.GsonRequest
import com.dnedev.photoeditor.utils.RequestQueueUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotosRemoteSource @Inject constructor(private val requestQueueUtil: RequestQueueUtil) :
    PhotosRepository.RemoteSource {

    override suspend fun getPhotos(
        url: String,
        headers: MutableMap<String, String>,
        photosResponseCallback: PhotosResponseCallback
    ) {
        withContext(Dispatchers.IO) {
            GsonRequest(
                url,
                PexelsDataResponse::class.java,
                headers,
                { response: PexelsDataResponse ->
                    photosResponseCallback.handlePhotosSuccessfulResponse(response)
                },
                { error ->
                    photosResponseCallback.handlePhotosErrorResponse(error)
                }).let { request ->
                requestQueueUtil.addRequest(request)
            }
        }
    }
}