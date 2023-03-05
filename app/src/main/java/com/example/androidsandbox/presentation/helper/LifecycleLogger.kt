package com.example.androidsandbox.presentation.helper

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

interface LifecycleLogger {
    fun registerLifecycleOwner(owner: LifecycleOwner, callerName: String?)
}

class LifecycleLoggerImpl : LifecycleLogger, LifecycleEventObserver {

    private lateinit var callerName: String
    override fun registerLifecycleOwner(owner: LifecycleOwner, callerName: String?) {
        owner.lifecycle.addObserver(this)
        this.callerName = callerName ?: "noName"
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> println("$callerName **** ON_CREATE")
            Lifecycle.Event.ON_DESTROY -> println("$callerName **** ON_DESTROY")
            Lifecycle.Event.ON_RESUME -> println("$callerName **** ON_RESUME")
            Lifecycle.Event.ON_PAUSE -> println("$callerName **** ON_PAUSE")
            Lifecycle.Event.ON_START -> println("$callerName **** ON_START")
            Lifecycle.Event.ON_STOP -> println("$callerName **** ON_STOP")
            Lifecycle.Event.ON_ANY -> println("$callerName **** ON_ANY")
        }
    }
}