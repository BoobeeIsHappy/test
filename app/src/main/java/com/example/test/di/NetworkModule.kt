package com.example.test.di

import com.example.test.data.BookService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.*
import java.util.concurrent.TimeUnit

val networkModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("http://api.ookbee.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createClient())
            .build()
    }

    single {
        get<Retrofit>().create<BookService>()
    }
}

private fun createClient(): OkHttpClient {
    val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
    okHttpClientBuilder.apply {
        addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.apply {
                addHeader(
                    "Authorization",
                    ""
                )
            }
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }
    return okHttpClientBuilder.build()
}