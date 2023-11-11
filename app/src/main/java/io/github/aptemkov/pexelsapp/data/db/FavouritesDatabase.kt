package io.github.aptemkov.pexelsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.aptemkov.pexelsapp.app.di.Dao
import io.github.aptemkov.pexelsapp.data.models.Photo

@Database(entities = [Photo::class], version = 1)
abstract class FavouritesDatabase : RoomDatabase() {
    abstract val dao: Dao
}