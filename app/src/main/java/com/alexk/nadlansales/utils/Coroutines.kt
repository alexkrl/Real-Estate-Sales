package com.alexk.nadlansales.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by alexkorolov on 17/03/2020.
 */
object Coroutines {

    fun main(work: suspend (() -> Unit)) = CoroutineScope(Dispatchers.Main).launch(exceptionHandler) {
        work()
    }


    fun io(work: suspend (() -> Unit)) = CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
        work()
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("ALEX_TAG - AddressSearchViewModel-> ${exception.message}")
    }

}