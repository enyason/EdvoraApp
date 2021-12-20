package com.enyason.edvoraapp.core.domain.usecase

import com.enyason.edvoraapp.core.api.EdvoraApi
import com.enyason.edvoraapp.core.domain.ProductInformation

interface GetProducts {

    suspend fun execute(): Result

    sealed class Result {
        class Success(val products: List<ProductInformation>) : Result()
        object Error : Result()
    }
}

class GetProductsImpl(private val edvoraApi: EdvoraApi) : GetProducts {
    override suspend fun execute(): GetProducts.Result {
        return kotlin.runCatching {
            val products = edvoraApi.getProducts().map { it.toDomain() }
            GetProducts.Result.Success(products)
        }.getOrElse {
            GetProducts.Result.Error
        }
    }
}