package com.example.groceryapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductList(
    val title: String,
    val cost: Double
): Parcelable