package com.enyason.edvoraapp.presentation.productList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enyason.edvoraapp.core.domain.usecase.GetProducts
import com.enyason.edvoraapp.core.domain.ProductInformation
import com.enyason.edvoraapp.common.extensions.asLiveData
import com.enyason.edvoraapp.core.domain.Category
import com.enyason.edvoraapp.presentation.utils.Dispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductsViewModel constructor(
    private val getProducts: GetProducts,
    private val dispatcher: Dispatcher
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState = _viewState.asLiveData()

    private val _loading = MutableLiveData<Boolean>()
    val loading = _loading.asLiveData()

    fun getProducts() {
        _loading.value = true
        viewModelScope.launch(dispatcher.io) {

            val viewState = when (val response = getProducts.execute()) {
                is GetProducts.Result.Success -> {
                    if (response.products.isEmpty()) ViewState.NoProductsFound
                    else ViewState.ProductCategories(response.products.toCategoriesList())
                }
                GetProducts.Result.Error -> ViewState.Error
            }

            withContext(dispatcher.main) {
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