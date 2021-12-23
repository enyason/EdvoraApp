package com.enyason.edvoraapp.presentation.di

import com.enyason.edvoraapp.presentation.productList.ProductsViewModel
import com.enyason.edvoraapp.presentation.utils.AppDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        ProductsViewModel(getProducts = get(), AppDispatcher())
    }
}