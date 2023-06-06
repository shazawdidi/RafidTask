package com.shazalia.rafidtask.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shazalia.rafidtask.model.Books
import com.shazalia.rafidtask.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * @Author: shazawdidi
 * @Date: 6/2/2023
 */
class MainViewModel(private val repository: Repository) : ViewModel() {

    val myResponse: MutableLiveData<Response<Books>> = MutableLiveData()

    fun getBooks(title: String, apiKey: String) {
        viewModelScope.launch {
            val response: Response<Books> = repository.getBooks(title, apiKey)
            myResponse.value = response
             Log.d("googleBook", "$response")

        }
    }
}