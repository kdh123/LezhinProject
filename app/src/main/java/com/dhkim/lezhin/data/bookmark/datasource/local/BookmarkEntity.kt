package com.dhkim.lezhin.data.bookmark.datasource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dhkim.lezhin.domain.bookmark.model.Bookmark

@Entity
data class BookmarkEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String
) {
    fun toData(): Bookmark {
        return Bookmark(
            id = id,
            thumbnailUrl = thumbnailUrl
        )
    }
}