package com.example.test.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookModel(
    var bookId: Int = 0,
    var bookName: String? = "",
    var bookAuthor: String? = "",
    var bookPrice: Float? = 0f
) : Parcelable