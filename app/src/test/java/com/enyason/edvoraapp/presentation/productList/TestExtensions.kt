package com.enyason.edvoraapp.presentation.productList

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.mockk.mockk

fun <T> LiveData<T>.observeWithMock(): Observer<T> {
    val observer: Observer<T> = mockk(relaxed = true)
    this.observeForever(observer)
    return observer
}
