package io.github.aptemkov.pexelsapp.data.api

import io.github.aptemkov.pexelsapp.data.api.ApiClient.apiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.pexels.com/v1/"
const val API_KEY = "81ivLjGtaFAGyrmTrY2ewFdVRlMU8nHVaXOdNtrMnOXXNuntf00sserG"


object ApiClient {
    val apiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}

fun main() {
    val apiKey = API_KEY
    val query = "house"
    val per_page = 30
    val page = 1

    runBlocking(Dispatchers.IO) {
        try {
            val response = apiService.getPhotos(apiKey, query, per_page, page)

            println(response)
            response.photos.forEach { photo ->
                println("c - Photo ID: ${photo.id}, Photographer: ${photo.photographer}, Photo: ${photo.url}")
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        println("========")
    }

    runBlocking(Dispatchers.IO) {
        try {
            val response = apiService.getFeaturedCollections(apiKey, page, per_page)

            println(response)
            response.collections.forEach {
                println("${it.title}")
            }

        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        println("========")
    }


    runBlocking(Dispatchers.IO) {
        try {
            val response = apiService.getCuratedPhotos(apiKey, per_page, page)

            println(response)
            response.photos.forEach { photo ->
                println("c - Photo ID: ${photo.id}, Photographer: ${photo.photographer}, Photo: ${photo.url}")
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        println("========")
    }

    runBlocking(Dispatchers.IO) {
        try {
            val response = apiService.getPhotoById(apiKey, 1029599)

            println(response)

        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        println("========")
    }


}

