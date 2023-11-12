package io.github.aptemkov.pexelsapp.app.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aptemkov.pexelsapp.domain.models.FeaturedCollectionDomain
import io.github.aptemkov.pexelsapp.domain.models.PhotoDomain
import io.github.aptemkov.pexelsapp.domain.repository.DataRepository
import io.github.aptemkov.pexelsapp.utils.Constants.DEFAULT_PER_PAGE
import io.github.aptemkov.pexelsapp.utils.Constants.TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {
    val photos = MutableStateFlow<List<PhotoDomain>>(listOf())
    val featuredState = MutableStateFlow<List<FeaturedCollectionDomain>>(listOf())
    val searchText = MutableStateFlow<String>("")
    val selectedFeaturedCollectionId = MutableStateFlow<String>("")

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            getFeaturedCollections()
            getCuratedPhotos()
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
            query = text,
            page = 1,
            per_page = DEFAULT_PER_PAGE
        )
        photos.emit(newPhotosList)
    }

    suspend fun getFeaturedCollections() {
        val featuredCollections = dataRepository.getFeaturedCollectionsList(
            page = 1,
            per_page = 7
        )
        featuredState.emit(featuredCollections)
    }

    suspend fun getCuratedPhotos() {
        val curatedPhotos = dataRepository.getCuratedPhotosList(
            per_page = DEFAULT_PER_PAGE,
            page = 1
        )
        photos.emit(curatedPhotos)
    }


}