package com.example.coroutineflow.lessons.lesson14

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

suspend fun main() {
    val scope = CoroutineScope(Dispatchers.Default)

//    val flow = MutableSharedFlow<Int>()
    val flow = MutableStateFlow(0)

    val producer = scope.launch {
        delay(500)
        repeat(10) {
            println("Emitted $it")
            flow.emit(it)
            println("After emitted $it")
            delay(200)
        }
    }

    val consumer = scope.launch {
        flow.collectLatest {
            println("Collected started: $it")
            delay(5000)
            println("Collected finished: $it")
        }
    }

    producer.join()
    consumer.join()
}