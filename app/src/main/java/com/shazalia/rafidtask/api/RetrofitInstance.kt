package com.shazalia.rafidtask.api


import com.shazalia.rafidtask.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @Author: shazawdidi
 * @Date: 6/2/2023
 */



object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: BooksApiInterface by lazy {
        retrofit.create(BooksApiInterface::class.java)
    }

}