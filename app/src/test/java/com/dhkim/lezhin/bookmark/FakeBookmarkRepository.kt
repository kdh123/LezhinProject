package com.dhkim.lezhin.bookmark

import com.dhkim.lezhin.domain.bookmark.BookmarkRepository
import com.dhkim.lezhin.domain.bookmark.model.Bookmark
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeBookmarkRepository : BookmarkRepository {

    private var bookmarks: MutableList<Bookmark> = mutableListOf()

    override suspend fun getBookmarks(): Flow<List<Bookmark>> {
        return flowOf(bookmarks)
    }

    override suspend fun saveBookmark(bookmark: Bookmark) {
        bookmarks.add(bookmark)
    }

    override suspend fun deleteBookmark(id: String) {
        bookmarks = bookmarks.filter { it.id != id }.toMutableList()
    }

    override suspend fun deleteAllBookmark() {
        bookmarks.clear()
    }
}