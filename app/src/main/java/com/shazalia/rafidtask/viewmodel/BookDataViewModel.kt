package com.shazalia.rafidtask.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.shazalia.rafidtask.model.BookSearchResultData
import com.shazalia.rafidtask.model.BookDatabase
import com.shazalia.rafidtask.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @Author: shazawdidi
 * @Date: 6/2/2023
 */
class BookDataViewModel(application: Application) :
    AndroidViewModel(application) {

    val readAllData: LiveData<List<BookSearchResultData>>
    private val repository: BookRepository

    init {
        val bookDao = BookDatabase.getDatabase(application).bookDao()
        repository = BookRepository(bookDao)
        readAllData = repository.readAllData
    }

    fun addBook(book: BookSearchResultData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBook(book)
        }
    }

    fun deleteBook(book: BookSearchResultData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBook(book)
        }
    }
}