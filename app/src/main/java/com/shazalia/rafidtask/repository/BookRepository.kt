package com.shazalia.rafidtask.repository

import androidx.lifecycle.LiveData
import com.shazalia.rafidtask.model.BookSearchResultData
import com.shazalia.rafidtask.api.room.BookDao

/**
 * @Author: shazawdidi
 * @Date: 6/2/2023
 */
class BookRepository(private val bookDao: BookDao) {

    val readAllData: LiveData<List<BookSearchResultData>> = bookDao.readAllData()

    suspend fun addBook(book: BookSearchResultData) {
        bookDao.addBook(book)
    }

    suspend fun deleteBook(book: BookSearchResultData) {
        bookDao.deleteBook(book)
    }
}