package com.dnedev.photoeditor.data

import com.google.gson.annotations.SerializedName

data class PexelsDataResponse(
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val photos: List<PhotoResponse>,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("next_page")
    val nextPage: String,
    @SerializedName("prev_page")
    val previousPage: String
)