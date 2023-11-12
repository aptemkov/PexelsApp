package io.github.aptemkov.pexelsapp.app.presentation.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import io.github.aptemkov.pexelsapp.R
import io.github.aptemkov.pexelsapp.app.presentation.common.HorizontalProgressBar
import io.github.aptemkov.pexelsapp.domain.models.FeaturedCollectionDomain
import io.github.aptemkov.pexelsapp.domain.models.PhotoDomain
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val photos by viewModel.photos.collectAsStateWithLifecycle()
    val featuredList by viewModel.featuredState.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val selectedFeaturedCollectionId by viewModel.selectedFeaturedCollectionId.collectAsStateWithLifecycle()

    var active by remember { mutableStateOf(false) }
    val history = remember { mutableStateListOf<String>() }

    val window = (LocalContext.current as Activity).window
    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()

    Column {
        CustomSearchBar(
            text = searchText,
            active = active,
            history = history,
            onQueryChanged = {
                viewModel.changeSearchText(it)
            },
            onSearch = {
                history.add(index = 0, element = it)
                active = false
                coroutineScope.launch {
                    if(it.isNotBlank()) {
                        viewModel.searchPhotos(it)
                    } else {
                        viewModel.getCuratedPhotos()
                    }
                }
            },
            onActiveChanged = {
                active = it
            }
        )

        FeaturedItemsBlock(
            selectedId = selectedFeaturedCollectionId,
            items = featuredList,
            changeSearchText = { title, id ->
                viewModel.changeSearchText(title)
                viewModel.changeSelectedId(id)
                coroutineScope.launch {
                    viewModel.searchPhotos(title)
                }
            },
        )

        HorizontalProgressBar(
            loading = photos.isEmpty()
        )
        if (photos.isNotEmpty()) {
            PhotosBlock(
                photos = photos,
                onPhotoClicked = {
                    navController.navigate("details/$it")
                }
            )
        } else {
            EmptyScreen(
                onRetryClicked = {
                    coroutineScope.launch {
                        if (searchText.isNotBlank()) {
                            viewModel.searchPhotos(searchText)
                        } else {
                            viewModel.getCuratedPhotos()
                        }
                    }
                }
            )
        }
    }
}

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


@Composable
fun PhotosBlock(
    photos: List<PhotoDomain>,
    onPhotoClicked: (String) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 64.dp),
        columns = StaggeredGridCells.Fixed(2),
    ) {
        items(photos) {
            PhotoItem(
                url = it.src.medium,
                onClick = onPhotoClicked,
                id = it.id
            )
        }
    }
}

@Composable
fun PhotoItem(
    id: Int,
    url: String,
    onClick: (String) -> Unit = {},
) {
    AsyncImage(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                onClick(id.toString())
            },
        model = url,
        contentDescription = "Image",
        contentScale = ContentScale.FillWidth,
        placeholder = if (isSystemInDarkTheme()) painterResource(id = R.drawable.placeholder_dark)
        else painterResource(id = R.drawable.placeholder_light)
    )
}

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    active: Boolean,
    text: String,
    history: SnapshotStateList<String>,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    onActiveChanged: (Boolean) -> Unit,
) {
    LaunchedEffect(text) {
        delay(2000L)
        onSearch(text)
    }

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .padding(horizontal = if (active) 0.dp else 8.dp),
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
        history.forEach {
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