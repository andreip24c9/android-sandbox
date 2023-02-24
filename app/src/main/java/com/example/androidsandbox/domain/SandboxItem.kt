package com.example.androidsandbox.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SandboxItem(val id: String, val label: String, initialCheck: Boolean = false) {
    var checked by mutableStateOf(initialCheck)
}