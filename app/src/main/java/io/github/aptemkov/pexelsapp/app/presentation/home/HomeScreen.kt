package io.github.aptemkov.pexelsapp.app.presentation.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import io.github.aptemkov.pexelsapp.domain.models.FeaturedCollectionDomain
import io.github.aptemkov.pexelsapp.domain.models.PhotoDomain
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
                    viewModel.searchPhotos(it)
                }
            },
            onActiveChanged = {
                active = it
            }
        )

        FeaturedItemsBlock(
            selectedId = selectedFeaturedCollectionId,
            items = featuredList,
            changeSearchText = {
                viewModel.changeSearchText(it)
                coroutineScope.launch {
                    viewModel.searchPhotos(it)
                }
            },
            changeSelectedId = {
                viewModel.changeSelectedId(it)
            }
        )


        PhotosBlock(
            photos = photos,
            onPhotoClicked = {
                navController.navigate("details/$it")
            }
        )
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
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun FeaturedItemsBlock(
    selectedId: String,
    items: List<FeaturedCollectionDomain>,
    changeSearchText: (String) -> Unit,
    changeSelectedId: (String) -> Unit,
) {
    //var selectedId by remember { mutableStateOf("") }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(11.dp),

        ) {
        items(items) { it ->
            FeaturedItem(
                id = it.id,
                title = it.title,
                onClick = { id ->
                    changeSearchText(it.title)
                    changeSelectedId(it.id)
                    Log.i("testtest", "FeaturedItemsBlock: ${it.id} $selectedId")
                },
                selected = it.id == selectedId
            )
        }

    }
}

@Composable
fun FeaturedItem(
    id: String,
    title: String,
    selected: Boolean,
    onClick: (String) -> Unit
) {
    val background =
        if (selected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary

    val textColor = if (selected) Color.White else MaterialTheme.colorScheme.onBackground

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(background)
            .clickable { onClick(id) },
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
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 24.dp, end = 24.dp),
        query = text,
        onQueryChange = onQueryChanged,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChanged,
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = "Search icon"
            )
        },
        trailingIcon = {
            if (active) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Search icon",
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
                    contentDescription = "History icon",
                )
                Text(text = it)
            }
        }
    }
}