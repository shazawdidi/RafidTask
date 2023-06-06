package com.shazalia.rafidtask.api.room

/**
 * @Author: shazawdidi
 * @Date: 6/2/2023
 */



import androidx.lifecycle.LiveData
import androidx.room.*
import com.shazalia.rafidtask.model.BookSearchResultData

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBook(books: BookSearchResultData)

    @Query("SELECT * FROM book_data ORDER BY id ASC")
    fun readAllData(): LiveData<List<BookSearchResultData>>

    @Delete
    suspend fun deleteBook(book: BookSearchResultData)
}