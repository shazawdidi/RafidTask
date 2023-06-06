package com.shazalia.rafidtask.repository

import com.shazalia.rafidtask.api.RetrofitInstance
import com.shazalia.rafidtask.model.Books
import retrofit2.Response

/**
 * @Author: shazawdidi
 * @Date: 6/2/2023
 */
class Repository {
    suspend fun getBooks(title: String, apiKey: String): Response<Books> {
        return RetrofitInstance.api.getBooks(title, apiKey)
    }
}