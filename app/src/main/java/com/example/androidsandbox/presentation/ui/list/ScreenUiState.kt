package com.example.androidsandbox.presentation.ui.list

import com.example.androidsandbox.domain.SandboxItem

sealed class SandboxScreenUiState {
    object Loading : SandboxScreenUiState()
    data class Error(val errorLabel: String) : SandboxScreenUiState()
    data class Data(
        val searchQuery: String,
        val sandboxItems: List<SandboxItem>,
        val isLoading: Boolean
    ) : SandboxScreenUiState()

    companion object {
        val Default = Loading
    }
}

interface SandboxScreenUiEvents {
    fun onSearchQueryChange(newQuery: String)
    fun onSearchClick()
    fun onItemCheckChange(sandboxItem: SandboxItem, isChecked: Boolean)
    fun onItemCloseClick(sandboxItem: SandboxItem)
    fun onClearClick()
}