package com.dhkim.lezhin.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dhkim.lezhin.data.bookmark.datasource.local.BookmarkEntity
import com.dhkim.lezhin.data.bookmark.datasource.local.BookmarkDao

@Database(entities = [BookmarkEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao
}