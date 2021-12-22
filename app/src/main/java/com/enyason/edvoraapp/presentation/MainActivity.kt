package com.enyason.edvoraapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enyason.edvoraapp.databinding.MainActivityBinding
import com.enyason.edvoraapp.presentation.productList.MainScreen
import com.enyason.edvoraapp.presentation.productList.ProductsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: ProductsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.composeView.setContent { MainScreen(viewModel) }
    }
}