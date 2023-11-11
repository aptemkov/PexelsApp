package io.github.aptemkov.pexelsapp.app.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.aptemkov.pexelsapp.data.api.ApiService
import io.github.aptemkov.pexelsapp.data.db.FavouritesDatabase
import io.github.aptemkov.pexelsapp.data.repository.DatabaseRepositoryImpl
import io.github.aptemkov.pexelsapp.data.repository.NetworkDataRepositoryImpl
import io.github.aptemkov.pexelsapp.domain.repository.DataRepository
import io.github.aptemkov.pexelsapp.domain.repository.DatabaseRepository
import io.github.aptemkov.pexelsapp.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkDataRepository(apiService: ApiService): DataRepository {
        return NetworkDataRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideFavouritesDatabase(app: Application): FavouritesDatabase {
        return Room.databaseBuilder(
            app,
            FavouritesDatabase::class.java,
            "favourites.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(db: FavouritesDatabase): DatabaseRepository {
        return DatabaseRepositoryImpl(db)
    }

}