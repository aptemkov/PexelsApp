package io.github.aptemkov.pexelsapp.app.presentation.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aptemkov.pexelsapp.domain.models.PhotoDomain
import io.github.aptemkov.pexelsapp.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    val favouritePhotos = MutableStateFlow<List<PhotoDomain>>(listOf())

    init {
        viewModelScope.launch {
            getFavouritePhotos()
        }
    }

    suspend fun getFavouritePhotos() {
        val photos = databaseRepository.getPhotosList()
        favouritePhotos.emit(photos)
    }

}