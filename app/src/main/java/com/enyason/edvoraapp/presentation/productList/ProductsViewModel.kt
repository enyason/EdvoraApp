package com.enyason.edvoraapp.presentation.productList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enyason.edvoraapp.core.domain.usecase.GetProducts
import com.enyason.edvoraapp.core.domain.ProductInformation
import com.enyason.edvoraapp.common.extensions.asLiveData
import com.enyason.edvoraapp.core.domain.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private fun getProducts() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {

            val viewState = when (val response = getProducts.execute()) {
                is GetProducts.Result.Success -> {
                    if (response.products.isEmpty()) ViewState.NoProductsFound
                    else ViewState.ProductCategories(response.products.toCategoriesList())
                }
                GetProducts.Result.Error -> ViewState.Error
            }

            withContext(Dispatchers.Main) {
                _viewState.value = viewState
                _loading.value = false
            }

        }
    }

    private fun List<ProductInformation>.toCategoriesList(): List<Category> {
        return this.groupBy { it.productName }.map { Category(name = it.key, products = it.value) }
    }

    sealed class ViewState {
        class ProductCategories(val productCategories: List<Category>) : ViewState()
        object NoProductsFound : ViewState()
        object Error : ViewState()
    }
}