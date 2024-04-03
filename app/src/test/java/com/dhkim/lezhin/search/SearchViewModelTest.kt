package com.dhkim.lezhin.search


import androidx.paging.testing.asSnapshot
import com.dhkim.lezhin.MainCoroutineRule
import com.dhkim.lezhin.bookmark.FakeBookmarkRepository
import com.dhkim.lezhin.domain.bookmark.BookmarkRepository
import com.dhkim.lezhin.domain.bookmark.model.Bookmark
import com.dhkim.lezhin.domain.search.SearchRepository
import com.dhkim.lezhin.domain.search.model.Image
import com.dhkim.lezhin.presentation.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchRepository: SearchRepository
    private lateinit var bookmarkRepository: BookmarkRepository

    private val images = (1..10).map {
        Image(
            id = "$it",
            thumbnailUrl = "url $it",
            isBookmark = false
        )
    }

    private val bookmarks = (1..3).map {
        Bookmark(
            id = "$it",
            thumbnailUrl = "url $it",
        )
    }

    @Before
    fun setup() = runTest {
        searchRepository = FakeSearchRepository().apply {
            addItems(images = images)
        }
        bookmarkRepository = FakeBookmarkRepository().apply {
            bookmarks.forEach {
                saveBookmark(it)
            }
        }

        searchViewModel = SearchViewModel(searchRepository, bookmarkRepository)
    }

    @Test
    fun `검색 결과 노출 테스트`() = runTest {
        bookmarkRepository.saveBookmark(bookmark = Bookmark(id = "1", "url 1"))
        bookmarkRepository.saveBookmark(bookmark = Bookmark(id = "2", "url 2"))
        bookmarkRepository.saveBookmark(bookmark = Bookmark(id = "3", "url 3"))

        val list = searchViewModel.resultFlow.asSnapshot()
        val bookmarksIds = bookmarks.map { it.id }

        assertTrue(list.count { it.isBookmark } == 3)
        assertEquals(list, images.map { if (bookmarksIds.contains(it.id)) it.copy(isBookmark = true) else it })
    }
}