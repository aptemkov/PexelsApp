package io.github.aptemkov.pexelsapp.domain.repository

import io.github.aptemkov.pexelsapp.data.models.Photo
import io.github.aptemkov.pexelsapp.domain.models.PhotoDomain


interface DatabaseRepository {

    suspend fun getPhotoById(id: Int): PhotoDomain?

    suspend fun getPhotosList(): List<PhotoDomain>

    suspend fun addFavouritePhoto(photo: Photo)

    suspend fun removeFavouritePhoto(photo: Photo)

    suspend fun countPhotosById(id: Int): Int
}