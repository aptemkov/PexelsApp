package io.github.aptemkov.pexelsapp.data.repository

import android.util.Log
import io.github.aptemkov.pexelsapp.data.api.ApiService
import io.github.aptemkov.pexelsapp.data.models.asDomain
import io.github.aptemkov.pexelsapp.domain.models.FeaturedCollectionDomain
import io.github.aptemkov.pexelsapp.domain.models.PhotoDomain
import io.github.aptemkov.pexelsapp.domain.repository.DataRepository
import io.github.aptemkov.pexelsapp.utils.Constants.API_KEY

class NetworkDataRepositoryImpl(private val apiService: ApiService) : DataRepository {
    override suspend fun getFeaturedCollectionsList(
        page: Int,
        per_page: Int

    ): List<FeaturedCollectionDomain> {
        val list = try {
             apiService
                .getFeaturedCollections(API_KEY, page, per_page)
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
        per_page: Int,
        page: Int
    ): List<PhotoDomain> {
        val list =  try{
            apiService
                .getPhotos(API_KEY, query, per_page, page)
                .photos
                .map { it.asDomain() }
        } catch (e: Exception) {
            Log.d("testtest", "getPhotosList: $e")
            listOf()
        }
        return list
    }

    override suspend fun getCuratedPhotosList(
        per_page: Int,
        page: Int
    ): List<PhotoDomain> {
        val list = try {
            apiService
                .getCuratedPhotos(API_KEY, per_page, page)
                .photos
                .map { it.asDomain() }
        } catch (e: Exception) {
            Log.d("testtest", "getCuratedPhotosList: $e")
            listOf()
        }
        return list
    }

    override suspend fun getPhotoById(id: Int): PhotoDomain? {
        val photo = try {
            apiService
                .getPhotoById(API_KEY, id)
                .asDomain()
        } catch (e: Exception) {
            null
        }
        return photo
    }
}