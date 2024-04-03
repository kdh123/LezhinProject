package com.dhkim.lezhin.data.bookmark.datasource.local

import com.dhkim.lezhin.data.room.AppDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkLocalDataSource @Inject constructor(private val db: AppDatabase) {

    private val service = db.bookmarkDao()

    fun getBookmarks(): Flow<List<BookmarkEntity>> {
        return service.getBookmarks()
    }

    fun saveBookmark(bookmarkEntity: BookmarkEntity) {
        service.saveBookmark(result = bookmarkEntity)
    }

    fun deleteBookmark(id: String) {
        service.deleteBookmark(id = id)
    }

    fun deleteAllBookmark() {
        service.deleteAllBookmark()
    }
}