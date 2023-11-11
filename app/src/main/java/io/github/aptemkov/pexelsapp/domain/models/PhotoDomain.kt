package io.github.aptemkov.pexelsapp.domain.models

import java.io.Serializable

data class PhotoDomain(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String?,
    val photographerUrl: String?,
    val photographerId: Int?,
    val avgColor: String?,
    val src: PhotoSrcDomain,
    val liked: Boolean,
    val alt: String

) : Serializable

data class PhotoSrcDomain(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)