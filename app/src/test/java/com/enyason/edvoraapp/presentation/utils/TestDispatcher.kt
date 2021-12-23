package com.enyason.edvoraapp.presentation.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcher : Dispatcher {
    override val io = TestCoroutineDispatcher()
    override val main = TestCoroutineDispatcher()
}