package com.example.androidsandbox.presentation.helper

import kotlin.reflect.KProperty

class MyLazyDelegate<out T : Any>(private val initialize: () -> T) {

    private var value: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return if (value == null) {
            value = initialize()
            value!!
        } else value!!
    }
}


class TestDelegate(private val block : () -> String) {
    private val value: String? = null
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return block()
    }
}