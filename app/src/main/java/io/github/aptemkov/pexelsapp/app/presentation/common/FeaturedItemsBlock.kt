package io.github.aptemkov.pexelsapp.app.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.aptemkov.pexelsapp.domain.models.FeaturedCollectionDomain

@Composable
fun FeaturedItemsBlock(
    selectedId: String,
    items: List<FeaturedCollectionDomain>,
    changeSearchText: (String, String) -> Unit,
) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(11.dp),

        ) {
        items(items) { it ->
            FeaturedItem(
                title = it.title,
                onClick = {
                    changeSearchText(it.title, it.id)
                },
                selected = it.id == selectedId
            )
        }

    }
}

@Composable
fun FeaturedItem(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val background =
        if (selected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary

    val textColor = if (selected) Color.White else MaterialTheme.colorScheme.onBackground

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(background)
            .clickable { onClick() },
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            text = title,
            color = textColor,
            fontSize = 14.sp
        )
    }
}