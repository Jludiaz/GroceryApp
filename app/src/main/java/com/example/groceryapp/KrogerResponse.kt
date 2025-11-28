package com.example.groceryapp

class KrogerResponse {
    data class StoresResponse(
        val data: List<Store>
    )

    data class Store(
        val locationId: String,
        val name: String,
        val address: Address
    )

    data class Address(
        val addressLine1: String?,
        val city: String?,
        val state: String?,
        val zipCode: String?
    )

    data class ProductsResponse(
        val data: List<Product>
    )
    data class Product(
        val productId: String,
        val description: String,
        val items: List<Item>?
    )

    data class Item(
        val price: Price?
    )

    data class Price(
        val regular: Double?,
        val promo: Double?
    )
}