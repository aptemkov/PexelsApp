package io.github.aptemkov.pexelsapp.app.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.github.aptemkov.pexelsapp.R
import io.github.aptemkov.pexelsapp.domain.models.PhotoDomain


@Composable
fun BookmarkedPhotosBlock(
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
            BookmarkedPhotoItem(
                url = it.src.medium,
                author = it.photographer ?: stringResource(R.string.name_surname),
                onClick = onPhotoClicked,
                id = it.id
            )
        }
    }
}

@Composable
fun BookmarkedPhotoItem(
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
            contentScale = ContentScale.FillWidth,
            placeholder = if (isSystemInDarkTheme()) painterResource(id = R.drawable.placeholder_dark)
            else painterResource(id = R.drawable.placeholder_light)
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