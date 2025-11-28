package com.example.groceryapp

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

// Use ViewModel to preserve UI state
class ListViewModel(private val groceryList: GroceryList) : ViewModel() {

    // State list of items
    var items = mutableStateListOf<ProductList>()
        private set

    // Retrieves title of grocery list
    val title: String
        get() = groceryList.title

    init {
        items.addAll(groceryList.items) // load initial items from grocery list
    }

    fun addItem(item: ProductList) {
        items.add(item)
        updateGroceryTotal()
    }

    fun removeItem(item: ProductList) {
        items.remove(item)
        updateGroceryTotal()
    }

    private fun updateGroceryTotal() {
        // Update the grocery list items to reflect current state
        groceryList.items.toMutableList().apply {
            clear()
            addAll(items)
        }
    }

    fun totalCost(): Double {
        return items.sumOf { it.cost }
    }
}