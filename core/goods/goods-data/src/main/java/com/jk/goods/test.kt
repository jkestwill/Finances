package com.jk.goods

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration

fun main(): Unit = runBlocking {
    val list = List(30) {
        it
    }
    val scope = CoroutineScope(Dispatchers.IO + Job())

    val j = scope.launch {
        for (i in list) {
            delay(10)
            println(i)

        }
    }
    j.join()
}