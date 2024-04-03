package com.dhkim.lezhin.presentation.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dhkim.lezhin.R
import com.dhkim.lezhin.domain.bookmark.model.Bookmark
import com.dhkim.lezhin.presentation.search.Thumbnail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(bookmarkViewModel: BookmarkViewModel = hiltViewModel()) {
    val bookmarks by bookmarkViewModel.bookmarkFlow.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.bookmark),
                        color = colorResource(id = R.color.white)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.purple_200))
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Button(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(10.dp),
                onClick = { bookmarkViewModel.deleteAllBookmark() }
            ) {
                Text(
                    text = stringResource(id = R.string.bookmark_all_delete),
                    color = colorResource(id = R.color.white),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            Box(modifier = Modifier.fillMaxSize()) {
                if (bookmarks.isEmpty()) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(id = R.string.bookmark_no_exist)
                    )
                } else {
                    Bookmarks(bookmarks = bookmarks, onBookmarkDelete = bookmarkViewModel::deleteBookmark)
                }
            }
        }
    }
}

@Composable
fun Bookmarks(bookmarks: List<Bookmark>, onBookmarkDelete: (String) -> Unit) {
    LazyVerticalStaggeredGrid(
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalItemSpacing = 4.dp,
        columns = StaggeredGridCells.Fixed(2),
        content = {
            itemsIndexed(
                items = bookmarks,
                key = { _, bookmark ->
                    bookmark.id
                }
            ) { index, bookmark ->
                val onClick: () -> Unit = remember {
                    {
                        onBookmarkDelete(bookmark.id)
                    }
                }

                Thumbnail(
                    tag = index,
                    imageUrl = bookmark.thumbnailUrl,
                    isBookmark = true,
                    onChangeBookmark = onClick
                )
            }
        }
    )
}
@Preview
@Composable
fun BookmarksPreview() {
    val list = mutableListOf<Bookmark>()

    Bookmarks(list) { _ ->

    }
}