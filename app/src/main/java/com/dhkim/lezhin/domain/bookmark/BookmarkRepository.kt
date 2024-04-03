package com.dhkim.lezhin.domain.bookmark

import com.dhkim.lezhin.domain.bookmark.model.Bookmark
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    suspend fun getBookmarks(): Flow<List<Bookmark>>
    suspend fun saveBookmark(bookmark: Bookmark)
    suspend fun deleteBookmark(id: String)
    suspend fun deleteAllBookmark()
}