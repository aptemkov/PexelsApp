package io.github.aptemkov.pexelsapp.app.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.aptemkov.pexelsapp.R

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
