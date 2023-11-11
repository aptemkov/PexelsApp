package io.github.aptemkov.pexelsapp.data.repository

import android.util.Log
import io.github.aptemkov.pexelsapp.data.api.ApiService
import io.github.aptemkov.pexelsapp.data.models.asDomain
import io.github.aptemkov.pexelsapp.domain.models.FeaturedCollectionDomain
import io.github.aptemkov.pexelsapp.domain.models.PhotoDomain
import io.github.aptemkov.pexelsapp.domain.repository.DataRepository

class DataRepositoryImpl(private val apiService: ApiService) : DataRepository {
    override suspend fun getFeaturedCollectionsList(
        apiKey: String,
        page: Int,
        per_page: Int

    ): List<FeaturedCollectionDomain> {
        val list = try {
             apiService
                .getFeaturedCollections(apiKey, page, per_page)
                .collections
                .asDomain()
        } catch (e:Exception) {
            Log.d("testtest", "getFeaturedCollectionsList: $e")
            listOf()
        }
        return list
    }

    override suspend fun getPhotosList(
        query: String,
        apiKey: String,
        per_page: Int,
        page: Int
    ): List<PhotoDomain> {
        val list =  try{
            apiService
                .getPhotos(apiKey, query, per_page, page)
                .photos
                .map { it.asDomain() }
        } catch (e: Exception) {
            Log.d("testtest", "getPhotosList: $e")
            listOf()
        }
        return list
    }

    override suspend fun getCuratedPhotosList(
        apiKey: String,
        per_page: Int,
        page: Int
    ): List<PhotoDomain> {
        val list = try {
            apiService
                .getCuratedPhotos(apiKey, per_page, page)
                .photos
                .map { it.asDomain() }
        } catch (e: Exception) {
            Log.d("testtest", "getCuratedPhotosList: $e")
            listOf()
        }
        return list
    }

    override suspend fun getPhotoById(apiKey: String, id: Int): PhotoDomain? {
        val photo = try {
            apiService
                .getPhotoById(apiKey, id)
                .asDomain()
        } catch (e: Exception) {
            null
        }
        return photo
    }
}