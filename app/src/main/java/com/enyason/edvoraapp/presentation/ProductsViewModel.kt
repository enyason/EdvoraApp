package com.enyason.edvoraapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enyason.edvoraapp.core.domain.usecase.GetProducts
import com.enyason.edvoraapp.core.domain.ProductInformation
import com.enyason.edvoraapp.common.extensions.asLiveData
import kotlinx.coroutines.launch

class ProductsViewModel constructor(
    private val getProducts: GetProducts
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState = _viewState.asLiveData()

    private val _loading = MutableLiveData<Boolean>()
    val loading = _loading.asLiveData()

    init {
        getProducts()
    }

    private fun getProducts() = viewModelScope.launch {
        _loading.value = true
        _viewState.value = when (val response = getProducts.execute()) {
            is GetProducts.Result.Success -> {
                if (response.products.isEmpty()) ViewState.NoProductsFound
                else ViewState.Products(response.products)
            }
            GetProducts.Result.Error -> ViewState.Error
        }
        _loading.value = false
    }

    sealed class ViewState {
        class Products(val products: List<ProductInformation>) : ViewState()
        object NoProductsFound : ViewState()
        object Error : ViewState()
    }
}