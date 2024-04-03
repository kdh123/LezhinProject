package com.dhkim.lezhin.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dhkim.lezhin.R
import com.dhkim.lezhin.domain.search.model.Image
import com.dhkim.lezhin.presentation.bookmark.BookmarkViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    bookmarkViewModel: BookmarkViewModel = hiltViewModel()
) {
    val query by searchViewModel.queryFlow.collectAsStateWithLifecycle()
    val searchResult = searchViewModel.resultFlow.collectAsLazyPagingItems()
    val onSearch: () -> Unit = remember {
        {
            searchViewModel.query(query = query)
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = searchResult.loadState) {
        if (searchResult.loadState.refresh is LoadState.Error) {
            snackbarHostState.showSnackbar(
                message = "Error: " + (searchResult.loadState.refresh as LoadState.Error).error.message,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.search),
                        color = colorResource(id = R.color.white)
                    )
                },
                colors = topAppBarColors(containerColor = colorResource(id = R.color.purple_200))
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column {
                SearchInput(query = query, onSearch = onSearch, onTextChange = searchViewModel::query)
                SearchResult(
                    images = searchResult,
                    onBookmarkDelete = bookmarkViewModel::deleteBookmark,
                    onBookmarkSave = bookmarkViewModel::saveBookmark
                )
            }
        }
    }
}

@Composable
fun SearchInput(query: String, onSearch: () -> Unit, onTextChange: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .testTag("testSearchInput"),
            value = query,
            onValueChange = {
                onTextChange(it)
            },
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                onSearch()
            }),
            placeholder = {
                Text(text = stringResource(id = R.string.search_input_hint))
            }
        )
    }
}

@Composable
fun SearchResult(
    images: LazyPagingItems<Image>?,
    onBookmarkDelete: (String) -> Unit,
    onBookmarkSave: (String, String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (images == null) {
            return@Box
        }

        if (images.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            if (images.loadState.append is LoadState.Loading && images.itemCount <= 0) {
                Text(
                    text = stringResource(id = R.string.search_result_no_exist),
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyVerticalStaggeredGrid(
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalItemSpacing = 4.dp,
                    columns = StaggeredGridCells.Fixed(2),
                    content = {
                        items(
                            count = images.itemCount,
                            key = images.itemKey(key = {
                                it.id
                            }),
                            contentType = images.itemContentType()
                        ) { index ->
                            val item = images[index]
                            if (item != null) {
                                Thumbnail(tag = index, imageUrl = item.thumbnailUrl, isBookmark = item.isBookmark) {
                                    if (item.isBookmark) {
                                        onBookmarkDelete(item.id)
                                    } else {
                                        onBookmarkSave(item.id, item.thumbnailUrl)
                                    }
                                }
                            }
                        }

                        if (images.loadState.append is LoadState.Loading) {
                            if (images.itemCount > 0) {
                                item(span = StaggeredGridItemSpan.FullLine) {
                                    Box(modifier = Modifier.fillMaxWidth()) {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .padding(vertical = 10.dp)
                                                .width(64.dp)
                                                .height(64.dp)
                                                .align(Alignment.Center),
                                            color = colorResource(id = R.color.purple_200),
                                            trackColor = colorResource(id = R.color.white),
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Thumbnail(tag: Int, imageUrl: String, isBookmark: Boolean, onChangeBookmark: () -> Unit) {
    Box {
        GlideImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .testTag(tag = "$tag"),
            contentScale = ContentScale.Crop,
        )

        Image(
            painter = painterResource(
                id = if (isBookmark) {
                    R.drawable.ic_bookmark
                } else {
                    R.drawable.ic_bookmark_border
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .padding(10.dp)
                .clickable {
                    onChangeBookmark()
                }
        )
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    Column {
        SearchInputPreview()
        SearchResultPreview()
    }
}

@Preview
@Composable
fun SearchInputPreview() {
    SearchInput(query = "hi", onSearch = { }, onTextChange = {})
}

@Preview
@Composable
fun SearchResultPreview() {
    val images = mutableListOf<Image>()

    for (i in 0..50) {
        images.add(
            Image(
                id = i.toString(),
                thumbnailUrl = "https://search3.kakaocdn.net/argon/130x130_85_c/EbDM2Fuh8C5",
                isBookmark = false
            )
        )
    }

    val flow = MutableStateFlow(PagingData.from(images))

    SearchResult(
        images = flow.collectAsLazyPagingItems(),
        onBookmarkDelete = {},
        onBookmarkSave = { _, _ -> }
    )
}

class ThumbnailPreviewParamProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(false, true)
}

@Preview
@Composable
fun ThumbnailPreview(
    @PreviewParameter(ThumbnailPreviewParamProvider::class) isBookmark: Boolean
) {
    Thumbnail(tag = 0, imageUrl = "", isBookmark = isBookmark, onChangeBookmark = {})
}