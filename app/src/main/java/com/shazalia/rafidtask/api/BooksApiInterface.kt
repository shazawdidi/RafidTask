package com.shazalia.rafidtask.api

import com.shazalia.rafidtask.model.Books
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: shazawdidi
 * @Date: 6/2/2023
 */

interface BooksApiInterface {

    @GET(" ")
    suspend fun getBooks(
        @Query("q") inTitle: String,
        @Query("key") apiKey: String
    ): Response<Books>
}