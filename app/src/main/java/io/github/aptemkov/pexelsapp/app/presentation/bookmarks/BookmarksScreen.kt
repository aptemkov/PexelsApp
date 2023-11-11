package io.github.aptemkov.pexelsapp.app.presentation.bookmarks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import io.github.aptemkov.pexelsapp.R
import io.github.aptemkov.pexelsapp.app.navigation.Screen
import io.github.aptemkov.pexelsapp.app.presentation.details.CustomToolBar
import io.github.aptemkov.pexelsapp.domain.models.PhotoDomain

@Composable
fun BookmarksScreen(
    navController: NavHostController,
    viewModel: BookmarksViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getFavouritePhotos()
    }

    val favouritePhotos by viewModel.favouritePhotos.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CustomToolBar(title = "Bookmarks", hasNavigation = false)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp, bottom = 64.dp),
        ) {
            if (favouritePhotos.isNotEmpty()) {
                FavouritePhotosBlock(
                    photos = favouritePhotos,
                    onPhotoClicked = {
                        navController.navigate("details/$it")
                    }
                )
            } else {
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

@Composable
fun EmptyScreen(
    message: String,
    onExploreClicked: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                text = message,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp
            )

            Text(
                text = stringResource(R.string.explore),
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 18.sp,
                modifier = Modifier.clickable {
                    onExploreClicked()
                }
            )
        }
    }
}

@Composable
fun FavouritePhotosBlock(
    photos: List<PhotoDomain>,
    onPhotoClicked: (String) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 64.dp),
        columns = StaggeredGridCells.Fixed(2),
    ) {
        items(photos) {
            FavouritePhotoItem(
                url = it.src.medium,
                author = it.photographer ?: stringResource(R.string.name_surname),
                onClick = onPhotoClicked,
                id = it.id
            )
        }
    }
}

@Composable
fun FavouritePhotoItem(
    id: Int,
    author: String,
    url: String,
    onClick: (String) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.BottomCenter

    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick(id.toString())
                },
            model = url,
            contentDescription = "Image",
            contentScale = ContentScale.FillWidth
        )
        Box(
            modifier = Modifier
                .height(32.dp)
                .background(Color.Black.copy(0.66f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = author,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White

            )
        }
    }
}