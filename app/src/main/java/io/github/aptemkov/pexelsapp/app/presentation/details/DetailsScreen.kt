package io.github.aptemkov.pexelsapp.app.presentation.details

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import io.github.aptemkov.pexelsapp.R
import io.github.aptemkov.pexelsapp.app.downloader.AndroidDownloader
import io.github.aptemkov.pexelsapp.app.navigation.Screen
import io.github.aptemkov.pexelsapp.app.presentation.bookmarks.EmptyScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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


    Scaffold(
        modifier = Modifier.padding(horizontal = 24.dp),
        topBar = {
            CustomToolBar(
                title = photo?.photographer ?: photo?.alt ?: "",
                hasNavigation = true,
                onNavigationItemClicked = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
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

@Composable
fun CustomToolBar(
    title: String,
    hasNavigation: Boolean,
    onNavigationItemClicked: () -> Unit = {}
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val contentColor = MaterialTheme.colorScheme.onBackground

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (hasNavigation) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                IconButton(onClick = onNavigationItemClicked) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back button",
                        tint = contentColor
                    )
                }
            }
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = contentColor,
        )
    }
}


@Composable
fun BottomBlock(
    isPhotoFavourite: Boolean,
    onDownloadClicked: () -> Unit,
    onFavouritesClicked: () -> Unit,
) {
    val background = MaterialTheme.colorScheme.background
    val primary = MaterialTheme.colorScheme.primary
    val tertiary = MaterialTheme.colorScheme.tertiary

    Row(
        modifier = Modifier
            .padding(vertical = 24.dp)
            .background(background)
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .width((screenWidth - 48.dp) / 2)
                .background(primary)
                .clickable { onDownloadClicked() },
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(tertiary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.FileDownload,
                    contentDescription = "Download button",
                    tint = Color.White
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.download),
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(primary)
                .clickable { onFavouritesClicked() },
            contentAlignment = Alignment.Center
        ) {

            val painter =
                if (isPhotoFavourite) R.drawable.ic_bookmark_active else R.drawable.ic_bookmark_inactive
            val contentColor =
                if (isPhotoFavourite) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary

            Icon(
                painter = painterResource(id = painter),
                contentDescription = "Bookmark button",
                tint = contentColor
            )
        }

    }
}

@Composable
fun DetailsPhoto(
    url: String?
) {
    if (url != null) {
        AsyncImage(
            modifier = Modifier
                .padding(top = 24.dp)
                .clip(RoundedCornerShape(24.dp))
                .fillMaxWidth(),
            model = url,
            contentDescription = "Image",
        )
    }
}