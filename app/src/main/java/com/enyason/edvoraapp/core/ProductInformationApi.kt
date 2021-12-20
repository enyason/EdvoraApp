package com.enyason.edvoraapp.core

import com.enyason.edvoraapp.core.domain.Address
import com.enyason.edvoraapp.core.domain.ProductInformation
import com.google.gson.annotations.SerializedName

data class ProductInformationApi(
    val address: AddressApi,
    @SerializedName("brand_name")
    val brandName: String,
    val date: String,
    @SerializedName("discription")
    val description: String,
    val image: String,
    val price: Int,
    @SerializedName("product_name")
    val productName: String,
    val time: String
) {

    fun toDomain(): ProductInformation {
        return ProductInformation(
            address = address.toDomain(),
            brandName,
            date,
            description,
            image,
            price,
            productName,
            time
        )
    }
}

data class AddressApi(
    val city: String,
    val state: String
) {
    fun toDomain() = Address(city, state)
}