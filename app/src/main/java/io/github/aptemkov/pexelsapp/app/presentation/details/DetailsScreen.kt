package io.github.aptemkov.pexelsapp.app.presentation.details

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import io.github.aptemkov.pexelsapp.R
import io.github.aptemkov.pexelsapp.app.downloader.AndroidDownloader
import io.github.aptemkov.pexelsapp.app.navigation.Screen
import io.github.aptemkov.pexelsapp.app.presentation.common.BottomBlock
import io.github.aptemkov.pexelsapp.app.presentation.common.CustomToolBar
import io.github.aptemkov.pexelsapp.app.presentation.common.DetailsPhoto
import io.github.aptemkov.pexelsapp.app.presentation.common.EmptyScreen
import io.github.aptemkov.pexelsapp.app.presentation.common.HorizontalProgressBar

@Composable
fun DetailsScreen(
    navController: NavController,
    id: String?,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val photo by viewModel.selectedPhoto.collectAsStateWithLifecycle()
    val isPhotoFavourite by viewModel.isPhotoFavourite.collectAsStateWithLifecycle()

    when (navController.previousBackStackEntry?.destination?.route) {
        Screen.Home.route -> {
            viewModel.getPhotoFromHomeScreen(id?.toInt())
        }

        Screen.Bookmarks.route -> {
            viewModel.getPhotoFromBookmarksScreen(id?.toInt())
        }
    }
    val downloader = AndroidDownloader(LocalContext.current)

    val window = (LocalContext.current as Activity).window
    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()

    Scaffold(
        modifier = Modifier.padding(horizontal = 24.dp).fillMaxSize(),
        topBar = {
            CustomToolBar(
                title = photo?.photographer ?: "",
                hasNavigation = true,
                onNavigationItemClicked = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            HorizontalProgressBar(loading = photo == null)
            if (photo != null) {
                DetailsPhoto(url = photo?.src?.original)

                BottomBlock(
                    isPhotoFavourite = isPhotoFavourite,
                    onDownloadClicked = {
                        downloader.downloadFile(photo!!.src.original, photo!!.alt.trim())
                    },
                    onFavouritesClicked = {
                        viewModel.onFavouriteButtonClicked(photo!!)
                    }
                )
            } else {
                EmptyScreen(
                    message = stringResource(R.string.image_not_found),
                    onExploreClicked = { navController.popBackStack() }
                )
            }

        }
    }

}
