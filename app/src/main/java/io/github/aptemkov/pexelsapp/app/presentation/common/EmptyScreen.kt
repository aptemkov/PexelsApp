package io.github.aptemkov.pexelsapp.app.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.aptemkov.pexelsapp.R

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