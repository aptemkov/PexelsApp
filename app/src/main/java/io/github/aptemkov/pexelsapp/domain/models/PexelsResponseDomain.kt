package io.github.aptemkov.pexelsapp.domain.models


data class PexelsResponseDomain(
    val page: Int,
    val perPage: Int,
    val photos: List<PhotoDomain>,
    val totalResults: Int,
    val nextPage: String?
)