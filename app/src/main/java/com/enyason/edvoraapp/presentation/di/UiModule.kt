package com.enyason.edvoraapp.presentation.di

import com.enyason.edvoraapp.presentation.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { ProductsViewModel(get()) }
}