package com.enyason.edvoraapp.core.domain

data class ProductInformation(
    val address: Address,
    val brandName: String,
    val date: String,
    val description: String,
    val image: String,
    val price: Int,
    val productName: String,
    val time: String
)

data class Address(
    val city: String,
    val state: String
)

data class Category(
    val name: String,
    val products: List<ProductInformation>
)
