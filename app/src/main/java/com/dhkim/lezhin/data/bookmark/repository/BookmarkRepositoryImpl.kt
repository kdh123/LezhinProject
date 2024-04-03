package com.dhkim.lezhin.data.bookmark.repository

import com.dhkim.lezhin.data.bookmark.datasource.local.BookmarkEntity
import com.dhkim.lezhin.data.bookmark.datasource.local.BookmarkLocalDataSource
import com.dhkim.lezhin.domain.bookmark.BookmarkRepository
import com.dhkim.lezhin.domain.bookmark.model.Bookmark
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val localDataSource: BookmarkLocalDataSource
) : BookmarkRepository {

    override suspend fun getBookmarks(): Flow<List<Bookmark>> {
        return localDataSource.getBookmarks().map { it.map { entity -> entity.toData() } }
    }

    override suspend fun saveBookmark(bookmark: Bookmark) {
        val entity = BookmarkEntity(
            id = bookmark.id,
            thumbnailUrl = bookmark.thumbnailUrl
        )

        localDataSource.saveBookmark(bookmarkEntity = entity)
    }

    override suspend fun deleteBookmark(id: String) {
        localDataSource.deleteBookmark(id)
    }

    override suspend fun deleteAllBookmark() {
        localDataSource.deleteAllBookmark()
    }
}