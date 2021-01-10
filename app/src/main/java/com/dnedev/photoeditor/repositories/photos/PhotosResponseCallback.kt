package com.dnedev.photoeditor.repositories.photos

import com.android.volley.VolleyError
import com.dnedev.photoeditor.data.PexelsDataResponse

interface PhotosResponseCallback {
    fun handlePhotosSuccessfulResponse(dataResponse: PexelsDataResponse)
    fun handlePhotosErrorResponse(errorResponse: VolleyError)
}