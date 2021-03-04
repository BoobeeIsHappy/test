package com.example.test.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.data.BookModel
import com.example.test.data.BookService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(val bookService: BookService) : ViewModel() {

    val books = MutableLiveData<MutableList<BookModel>>()

    fun requestBookService(userId: String) {
        val call = bookService.getBooksByUserId(userId)
        call.enqueue(object : Callback<MutableList<BookModel>?> {
            override fun onResponse(
                call: Call<MutableList<BookModel>?>,
                response: Response<MutableList<BookModel>?>
            ) {
                response.body()?.let { books.postValue(it) }
            }

            override fun onFailure(call: Call<MutableList<BookModel>?>, t: Throwable) {
                Log.d("error", t.message!!)
            }
        })
    }
}
