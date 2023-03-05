package com.example.androidsandbox.presentation.ui.list

import com.example.androidsandbox.domain.SandboxItem

sealed class ListScreenUiState {
    object Loading : ListScreenUiState()
    data class Error(val errorLabel: String) : ListScreenUiState()
    data class Data(
        val searchQuery: String,
        val sandboxItems: List<SandboxItem>,
        val isLoading: Boolean
    ) : ListScreenUiState()

    companion object {
        val Default = Loading
    }
}

interface ListScreenUiActions {
    fun onSearchQueryChange(newQuery: String)
    fun onSearchClick()
    fun onItemClick(sandboxItem: SandboxItem)
    fun onItemLongClick(sandboxItem: SandboxItem)
    fun onItemCheckChange(sandboxItem: SandboxItem, isChecked: Boolean)
    fun onItemCloseClick(sandboxItem: SandboxItem)
    fun onClearClick()
}

sealed class ListUiEvent(val key: Any) {
    data class ShowToast(val message: String) : ListUiEvent(message)
    data class NavigateToDetails(val sandboxItem: SandboxItem) : ListUiEvent(sandboxItem)
}