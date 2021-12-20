package com.enyason.edvoraapp.core.api

import com.enyason.edvoraapp.core.ProductInformationApi
import retrofit2.http.GET

interface EdvoraApi {

    @GET("/")
    suspend fun getProducts(): List<ProductInformationApi>
}