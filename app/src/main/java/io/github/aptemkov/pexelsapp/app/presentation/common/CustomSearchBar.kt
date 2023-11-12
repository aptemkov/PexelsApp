package io.github.aptemkov.pexelsapp.app.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.aptemkov.pexelsapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    active: Boolean,
    text: String,
    history: MutableSet<String>,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    onActiveChanged: (Boolean) -> Unit,
) {
//    LaunchedEffect(text) {
//        delay(2000L)
//        onSearch(text)
//    }
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .padding(horizontal = if (active) 0.dp else 24.dp),
        query = text,
        onQueryChange = onQueryChanged,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChanged,
        placeholder = {
            Text(text = stringResource(R.string.search_placeholder))
        },
        leadingIcon = {
            Icon(
                painterResource(id = R.drawable.ic_search),
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = stringResource(R.string.search_icon_description)
            )
        },
        trailingIcon = {
            if (active) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.search_icon_description),
                    modifier = Modifier.clickable {
                        if (text.isNotBlank()) {
                            onQueryChanged("")
                        } else {
                            onActiveChanged(false)
                        }
                    }
                )
            }

        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primary,
            inputFieldColors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.tertiary,
            )
        )
    ) {
        LazyRow() {
            items(items = history.toList()) {
                Row(
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .clickable {
                            onQueryChanged(it)
                        }
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        imageVector = Icons.Default.History,
                        contentDescription = stringResource(R.string.history_icon_description),
                    )
                    Text(text = it)
                }
            }
        }
    }
}