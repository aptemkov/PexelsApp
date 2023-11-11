package io.github.aptemkov.pexelsapp.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aptemkov.pexelsapp.data.api.API_KEY
import io.github.aptemkov.pexelsapp.data.models.FeaturedCollection
import io.github.aptemkov.pexelsapp.domain.models.FeaturedCollectionDomain
import io.github.aptemkov.pexelsapp.domain.models.PhotoDomain
import io.github.aptemkov.pexelsapp.domain.repository.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataRepository: DataRepository
): ViewModel() {
    val photos = MutableStateFlow<List<PhotoDomain>>(listOf())
    val featuredState = MutableStateFlow<List<FeaturedCollectionDomain>>(listOf())
    val searchText = MutableStateFlow<String>("")
    val selectedFeaturedCollectionId = MutableStateFlow<String>("")

    init {
        viewModelScope.launch {
            val featuredCollections = dataRepository.getFeaturedCollectionsList(
                apiKey = API_KEY,
                page = 1,
                per_page = 7
            )
            featuredState.emit(featuredCollections)

            val curatedPhotos = dataRepository.getCuratedPhotosList(
                apiKey = API_KEY,
                per_page = 30,
                page = 1
            )
            photos.emit(curatedPhotos)
        }
    }

    fun changeSearchText(text: String) {
        searchText.value = text
        selectedFeaturedCollectionId.value = ""
    }

    fun changeSelectedId(id: String) {
        selectedFeaturedCollectionId.value = id
    }

    suspend fun searchPhotos(text: String) {
        val newPhotosList = dataRepository.getPhotosList(
            apiKey = API_KEY,
            query = text,
            page = 1,
            per_page = 30
        )
        photos.emit(newPhotosList)
    }


}