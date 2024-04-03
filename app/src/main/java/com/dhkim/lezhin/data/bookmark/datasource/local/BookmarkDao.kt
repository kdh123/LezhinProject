package com.dhkim.lezhin.data.bookmark.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM bookmarkentity")
    fun getBookmarks(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBookmark(result: BookmarkEntity)

    @Query("DELETE FROM bookmarkentity WHERE `id` = :id")
    fun deleteBookmark(id: String)

    @Query("DELETE FROM bookmarkentity")
    fun deleteAllBookmark()
}