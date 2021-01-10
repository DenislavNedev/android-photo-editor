package com.dnedev.photoeditor.data

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    val id: Int,
    val url: String,
    val width: Int,
    val height: Int,
    val photographer: String,
    @SerializedName("photographer_url")
    val photographerUrl: String,
    @SerializedName("src")
    val photoSourceResponse: PhotoSourceResponse
)