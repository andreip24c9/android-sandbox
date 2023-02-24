package com.example.androidsandbox.presentation.ui.list

import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

val fromThread = "\t ************* from ${Thread.currentThread().name} thread"

fun main() {
    println("Main Program Start $fromThread")

    runBlocking {
        val simpleFlow = simpleFlow()
        launch {
            println("First collector")
            simpleFlow.collect {
                if (it.isSuccess) {
                    println("First Collect ${it.getOrNull()} $fromThread")
                } else {
                    println("First Collect error occurred: ${it.exceptionOrNull()?.message}")
                }
            }
        }

        launch {
            println("Second collector")
            simpleFlow.collect {
                if (it.isSuccess) {
                    println("Second Collect ${it.getOrNull()} $fromThread")
                } else {
                    println("Second Collect error occurred: ${it.exceptionOrNull()?.message}")
                }
            }
        }

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
    }
}

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