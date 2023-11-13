package io.github.aptemkov.pexelsapp.app.presentation.bookmarks

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.github.aptemkov.pexelsapp.R
import io.github.aptemkov.pexelsapp.app.navigation.Screen
import io.github.aptemkov.pexelsapp.app.presentation.common.BookmarkedPhotosBlock
import io.github.aptemkov.pexelsapp.app.presentation.common.CustomToolBar
import io.github.aptemkov.pexelsapp.app.presentation.common.EmptyScreen
import io.github.aptemkov.pexelsapp.app.presentation.common.HorizontalProgressBar

@Composable
fun BookmarksScreen(
    navController: NavHostController,
    viewModel: BookmarksViewModel = hiltViewModel()
) {
    val window = (LocalContext.current as Activity).window
    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()

    LaunchedEffect(Unit) {
        viewModel.getFavouritePhotos()
    }

    val favouritePhotos by viewModel.favouritePhotos.collectAsStateWithLifecycle()
    val isErrorState by viewModel.isErrorState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CustomToolBar(title = stringResource(R.string.bookmarks_title), hasNavigation = false)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            HorizontalProgressBar(loading = favouritePhotos.isEmpty() && !isErrorState)
            if (favouritePhotos.isNotEmpty() && !isErrorState) {
                BookmarkedPhotosBlock(
                    photos = favouritePhotos,
                    onPhotoClicked = {
                        navController.navigate("details/$it")
                    }
                )
            } else if(isErrorState){
                EmptyScreen(
                    message = stringResource(R.string.you_havent_saved_anything_yet),
                    onExploreClicked = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}
