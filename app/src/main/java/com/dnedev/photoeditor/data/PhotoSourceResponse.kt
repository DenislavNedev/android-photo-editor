package com.dnedev.photoeditor.data

data class PhotoSourceResponse(
    val original: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)
