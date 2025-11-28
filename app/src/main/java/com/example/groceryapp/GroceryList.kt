package com.example.groceryapp

import android.os.Parcelable // Allows transporting data from one activity to another
import kotlinx.parcelize.Parcelize

@Parcelize
class GroceryList(
    val title: String,
    val description: String,
    val totalCost: String,
    val items: List<ProductList> = emptyList() // hold the items
) : Parcelable {
    // Dynamic total cost
    fun totalCost(): Double {
        return items.sumOf { it.cost }
    }
}