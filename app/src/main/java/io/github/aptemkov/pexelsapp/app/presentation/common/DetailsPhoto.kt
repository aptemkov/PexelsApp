package io.github.aptemkov.pexelsapp.app.presentation.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.github.aptemkov.pexelsapp.R

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
            placeholder = if (isSystemInDarkTheme()) painterResource(id = R.drawable.placeholder_dark)
            else painterResource(id = R.drawable.placeholder_light)
        )
    }
}