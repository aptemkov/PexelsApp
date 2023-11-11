package io.github.aptemkov.pexelsapp.app.di

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.aptemkov.pexelsapp.data.models.Photo

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouritePhoto(photo: Photo)

    @Delete
    suspend fun removeFavouritePhoto(photo: Photo)

    @Query("SELECT * FROM favourite_photos")
    suspend fun getFavourites(): List<Photo>

    @Query("SELECT * FROM favourite_photos WHERE id LIKE :id")
    suspend fun getPhotoById(id: Int): List<Photo>

    @Query("SELECT COUNT(*) FROM favourite_photos WHERE id = :id")
    suspend fun countById(id: Int): Int

}