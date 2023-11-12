package io.github.aptemkov.pexelsapp.data.repository

import android.util.Log
import io.github.aptemkov.pexelsapp.data.db.FavouritesDatabase
import io.github.aptemkov.pexelsapp.data.models.Photo
import io.github.aptemkov.pexelsapp.data.models.asDomain
import io.github.aptemkov.pexelsapp.domain.models.PhotoDomain
import io.github.aptemkov.pexelsapp.domain.repository.DatabaseRepository
import io.github.aptemkov.pexelsapp.utils.Constants.TAG


class DatabaseRepositoryImpl(
    private val db: FavouritesDatabase
) : DatabaseRepository {
    override suspend fun getPhotoById(id: Int): PhotoDomain? {
        val photo = try {
            db.dao.getPhotoById(id).first()
        } catch (e: Exception) {
            null
        }
        return photo?.asDomain()
    }

    override suspend fun getPhotosList(): List<PhotoDomain> {
        val photos = try {
            db.dao.getFavourites()
        } catch (e: Exception) {
            listOf()
        }
        return photos.map { it.asDomain() }
    }

    override suspend fun addFavouritePhoto(photo: Photo) {
        try {
            db.dao.addFavouritePhoto(photo)
        } catch (e: Exception) {
            Log.i(TAG, "add exception: $e")
        }
    }

    override suspend fun removeFavouritePhoto(photo: Photo) {
        try {
            db.dao.removeFavouritePhoto(photo)
        } catch (e: Exception) {
            Log.i(TAG, "remove exception: $e")
        }
    }

    override suspend fun countPhotosById(id: Int): Int {
        val count = try {
            db.dao.countById(id)
        } catch (e: Exception) {
            Log.i(TAG, "count exception: $e")
            0
        }
        return count
    }

}