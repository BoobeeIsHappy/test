package com.example.test.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BookService {

    @GET("user/{userId}/books")
    fun getBooksByUserId(
        @Path("userId") userId: String,
    ): Call<MutableList<BookModel>>
}