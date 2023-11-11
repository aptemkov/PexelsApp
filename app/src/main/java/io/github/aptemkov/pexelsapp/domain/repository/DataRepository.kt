package io.github.aptemkov.pexelsapp.domain.repository

import io.github.aptemkov.pexelsapp.domain.models.FeaturedCollectionDomain
import io.github.aptemkov.pexelsapp.domain.models.PhotoDomain


interface DataRepository {
    suspend fun getFeaturedCollectionsList(
        apiKey: String,
        page: Int,
        per_page: Int
    ): List<FeaturedCollectionDomain>
    suspend fun getPhotosList(
        query: String,
        apiKey: String,
        per_page: Int,
        page: Int
    ): List<PhotoDomain>

    suspend fun getCuratedPhotosList(
        apiKey: String,
        per_page: Int,
        page: Int
    ): List<PhotoDomain>

    suspend fun getPhotoById(
        apiKey: String,
        id: Int
    ): PhotoDomain?
}