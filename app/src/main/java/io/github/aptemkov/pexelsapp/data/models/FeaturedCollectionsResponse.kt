package io.github.aptemkov.pexelsapp.data.models

import io.github.aptemkov.pexelsapp.domain.models.FeaturedCollectionDomain

data class FeaturedCollectionsResponse(
    val collections: List<FeaturedCollection>,
    val page: Int,
    val per_page: Int,
    val total_results: Int,
    val next_page: String,
    val prev_page: String
)

data class FeaturedCollection(
    val id: String,
    val title: String,
    val description: String?,
    val private: Boolean,
    val media_count: Int,
    val photos_count: Int,
    val videos_count: Int
)

fun List<FeaturedCollection>.asDomain(): List<FeaturedCollectionDomain> {
    return map {
        FeaturedCollectionDomain (
            id = it.id,
            title = it.title,
            description = it.description,
            private = it.private,
            media_count = it.media_count,
            photos_count = it.photos_count,
            videos_count = it.videos_count
        )
    }
}
