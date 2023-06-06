
package com.shazalia.rafidtask.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_data")
data class BookSearchResultData(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val bookSmallThumbnail: String?,
    val title: String,
    val publisher: String?,
    val bookDescription: String?,
    val previewLink: String?,
    val bookThumbnail: String?,
    var isFavourite: Boolean? = false
)