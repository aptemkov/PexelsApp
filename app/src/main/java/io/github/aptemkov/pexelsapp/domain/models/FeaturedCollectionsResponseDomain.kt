package io.github.aptemkov.pexelsapp.domain.models

data class FeaturedCollectionsResponseDomain(
    val collections: List<FeaturedCollectionDomain>,
    val page: Int,
    val per_page: Int,
    val total_results: Int,
    val next_page: String,
    val prev_page: String
)

data class FeaturedCollectionDomain(
    val id: String,
    val title: String,
    val description: String?,
    val private: Boolean,
    val media_count: Int,
    val photos_count: Int,
    val videos_count: Int
)