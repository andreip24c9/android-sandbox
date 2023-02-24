package com.example.androidsandbox.presentation.ui.list

import com.example.androidsandbox.domain.SandboxItem

data class UiState(
    val searchQuery: String,
    val sandboxItems: List<SandboxItem>,
    val isLoading: Boolean
)

interface UiEvent {
    fun onSearchQueryChange(newQuery: String)
    fun onSearchClick()
    fun onItemCheckChange(task: SandboxItem, checked: Boolean)
    fun onItemCloseClick(task: SandboxItem)
    fun onClearClick()
}


sealed class UiStateSealed {
    object Loading : UiStateSealed()
    data class Error(val error: String) : UiStateSealed()
    data class Data(
        val searchQuery: String,
        val sandboxItems: List<SandboxItem>,
        val isLoading: Boolean
    ) : UiStateSealed()
}