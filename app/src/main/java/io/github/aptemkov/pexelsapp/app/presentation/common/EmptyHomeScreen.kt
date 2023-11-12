package io.github.aptemkov.pexelsapp.app.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import io.github.aptemkov.pexelsapp.R

@Composable
fun EmptyScreen(
    onRetryClicked: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_no_network),
                contentDescription = stringResource(R.string.no_network_icon),
                tint = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = stringResource(R.string.explore),
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 18.sp,
                modifier = Modifier.clickable {
                    onRetryClicked()
                }
            )
        }
    }
}