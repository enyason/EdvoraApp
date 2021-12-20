package com.enyason.edvoraapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enyason.edvoraapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<ProductsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}