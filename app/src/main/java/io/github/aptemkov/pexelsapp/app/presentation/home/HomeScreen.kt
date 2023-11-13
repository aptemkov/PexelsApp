package io.github.aptemkov.pexelsapp.app.presentation.home

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import io.github.aptemkov.pexelsapp.R
import io.github.aptemkov.pexelsapp.app.presentation.common.CustomSearchBar
import io.github.aptemkov.pexelsapp.app.presentation.common.EmptyScreen
import io.github.aptemkov.pexelsapp.app.presentation.common.FeaturedItemsBlock
import io.github.aptemkov.pexelsapp.app.presentation.common.HorizontalProgressBar
import io.github.aptemkov.pexelsapp.app.presentation.common.PhotosBlock
import io.github.aptemkov.pexelsapp.utils.hasInternetConnection
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val isErrorState by viewModel.isErrorState.collectAsStateWithLifecycle()
    val photos by viewModel.photos.collectAsStateWithLifecycle()
    val featuredList by viewModel.featuredState.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val selectedFeaturedCollectionId by viewModel.selectedFeaturedCollectionId.collectAsStateWithLifecycle()

    var active by remember { mutableStateOf(false) }
    val history = remember { mutableSetOf<String>() }

    val context = LocalContext.current
    val window = (context as Activity).window
    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()

    if(!hasInternetConnection(context)) {
        Toast.makeText(context, stringResource(R.string.no_internet_connection), Toast.LENGTH_LONG).show()
    }

    Column {
        CustomSearchBar(
            text = searchText,
            active = active,
            history = history,
            onQueryChanged = {
                viewModel.changeSearchText(it)
            },
            onSearch = {
                history.add(element = it)
                active = false
                coroutineScope.launch {
                    if (it.isNotBlank()) {
                        viewModel.searchPhotos(it)
                    } else {
                        viewModel.getCuratedPhotos()
                    }
                }
            }
        ) {
            active = it
        }

        FeaturedItemsBlock(
            selectedId = selectedFeaturedCollectionId,
            items = featuredList,
            changeSearchText = { title, id ->
                viewModel.changeSearchText(title)
                viewModel.changeSelectedId(id)
                coroutineScope.launch {
                    viewModel.searchPhotos(title)
                }
            },
        )

        HorizontalProgressBar(
            loading = photos.isEmpty() && !isErrorState
        )
        if (photos.isNotEmpty() && !isErrorState) {
            PhotosBlock(
                photos = photos,
                onPhotoClicked = {
                    navController.navigate("details/$it")
                }
            )
        } else if(isErrorState) {
            EmptyScreen(
                onRetryClicked = {
                    coroutineScope.launch {
                        if (searchText.isNotBlank()) {
                            viewModel.searchPhotos(searchText)
                        } else {
                            viewModel.getCuratedPhotos()
                        }
                    }
                }
            )
        }
    }
}
