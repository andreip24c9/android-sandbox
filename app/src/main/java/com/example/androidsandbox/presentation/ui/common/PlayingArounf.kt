package com.example.androidsandbox.presentation.ui.list

import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

val fromThread = "\t ************* from ${Thread.currentThread().name} thread"
fun fromCoroutine(scope: CoroutineScope) =
    "\t ************* from ${scope.coroutineContext} scope context"

fun main() {
    println("Main Program Start $fromThread")
//    collectTimerValue()
//    restaurantCollector()
    collectStateFlow()
    collectSharedFlow()
}
//    runBlocking {
//        val simpleFlow = simpleFlow()
//        launch {
//            println("First collector")
//            simpleFlow.collect {
//                if (it.isSuccess) {
//                    println("First Collect ${it.getOrNull()} $fromThread")
//                } else {
//                    println("First Collect error occurred: ${it.exceptionOrNull()?.message}")
//                }
//            }
//        }
//
//        launch {
//            println("Second collector")
//            simpleFlow.collect {
//                if (it.isSuccess) {
//                    println("Second Collect ${it.getOrNull()} $fromThread")
//                } else {
//                    println("Second Collect error occurred: ${it.exceptionOrNull()?.message}")
//                }
//            }
//        }

// collectAsState
// stateIn
// flowOn
// combine
// collect
// conflate
// tryEmit
// mutableStateFlow
// mutableSharedFlow

//        println()
//        val simpleFlow2 = simpleFlow
//        println("Second collector")
//        simpleFlow2.collect {
//            println("Collect $it $fromThread")
//        }
//        println()
//    }
//}

// 'cold flows' are created on demand and create data only when they are being observed

fun simpleFlow(): Flow<Result<String>> = flow {
    for (i in 1..6) {
        delay(1500)
        println("Emit $i $fromThread")
        if (i == 4) {
            emit(Result.failure(Exception("Simple flow exception")))
        } else {
            emit(Result.success(i))
        }
    }
}.map {
    if (it.isSuccess) {
        Result.success("\"mapped (${it.getOrNull()})\"")
    } else {
        Result.failure(it.exceptionOrNull() ?: Exception("Exception from emission"))
    }
}.filter {
    !it.getOrThrow().contains("2")
}.catch { e ->
    println("Enter catch block with exception message: ${e.message}")
    emit(Result.failure(e))
}


fun collectTimerValue() {
    val countDownFlow = countDownTimer()
//    runBlocking {
//        countDownFlow.collect { value ->
//            println("Timer value: $value")
//        }
//    }

    runBlocking {
        countDownFlow.onEach {
            println("Timer value: $it")
        }.launchIn(this)
    }
}

fun countDownTimer() = flow {
    val initialValue = 5
    var currentValue = initialValue
    emit(initialValue)
    while (currentValue > 0) {
        delay(1000)
        currentValue--
        emit(currentValue)
    }
}


fun restaurantCollector() {
    val restaurantFlow = restaurantFlow()
    runBlocking {
        restaurantFlow.onEach {
            println("Delivered: $it")
        }.buffer(capacity = 1)
            .collect {
//                println("Now starting with: $it")
                delay(5000)
                println("Finished: $it $fromThread")
            }
    }
}

fun restaurantFlow() = flow {
    emit("drinks")
    delay(500)
    emit("appetizer")
    delay(500)
    emit("main course")
    delay(500)
    emit("desert")
    delay(500)
    emit("coffee")
}

private val _stateFlow = MutableStateFlow(0)
val stateFlow = _stateFlow

private val _sharedFlow = MutableSharedFlow<Int>()
val sharedFlow = _sharedFlow

fun collectStateFlow(): Nothing = runBlocking {
    stateFlow.collect {
        print("Collected $it")
    }
}

fun collectSharedFlow(): Nothing = runBlocking {
    sharedFlow.collect {
        print("Collected $it")
    }
}