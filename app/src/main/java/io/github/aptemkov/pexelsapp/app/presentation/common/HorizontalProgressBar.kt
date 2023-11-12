package io.github.aptemkov.pexelsapp.app.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalProgressBar(
    loading: Boolean,
) {
    AnimatedVisibility(
        visible = loading,
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(4.dp),
            color = MaterialTheme.colorScheme.tertiary,
            trackColor = MaterialTheme.colorScheme.primary
        )
    }
}