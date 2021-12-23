package com.enyason.edvoraapp.presentation.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface Dispatcher {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
}