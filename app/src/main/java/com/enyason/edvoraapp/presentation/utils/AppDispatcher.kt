package com.enyason.edvoraapp.presentation.utils

import kotlinx.coroutines.Dispatchers

class AppDispatcher : Dispatcher {
    override val io = Dispatchers.IO
    override val main = Dispatchers.Main
}