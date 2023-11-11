package io.github.aptemkov.pexelsapp.app.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aptemkov.pexelsapp.data.api.API_KEY
import io.github.aptemkov.pexelsapp.domain.models.PhotoDomain
import io.github.aptemkov.pexelsapp.domain.repository.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val dataRepository: DataRepository
): ViewModel() {

    val selectedPhoto = MutableStateFlow<PhotoDomain?>(null)

    init {
        viewModelScope.launch {
            val photo = dataRepository.getPhotoById(API_KEY, id)
        }
    }

}