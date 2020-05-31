package com.mosh.songfinder.presentation.viewmodels.coroutine

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@Suppress("PropertyName")
open class CoroutineContextProvider @Inject constructor() {
    open val Main: CoroutineContext by lazy {   Dispatchers.Main }
    open val IO: CoroutineContext by lazy {   Dispatchers.IO }
}