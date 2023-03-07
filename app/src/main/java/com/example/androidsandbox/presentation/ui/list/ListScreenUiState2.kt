package com.example.androidsandbox.presentation.ui.list

import com.example.androidsandbox.domain.SandboxItem

sealed class ListScreenUiState {
    data class Data(
        val searchQuery: String,
        val sandboxItems: List<SandboxItem>,
        val isLoading: Boolean
    ) : ListScreenUiState()

    object Loading : ListScreenUiState()
    data class Error(val errorLabel: String) : ListScreenUiState()

    companion object {
        val Default = Loading
    }
}

sealed interface ListScreenUiAction {
    data class OnItemClick(val sandboxItem: SandboxItem) : ListScreenUiAction
    data class OnItemLongClick(val sandboxItem: SandboxItem) : ListScreenUiAction
    data class OnItemCloseClick(val sandboxItem: SandboxItem) : ListScreenUiAction
    data class OnItemCheckChange(val sandboxItem: SandboxItem, val isChecked: Boolean) :
        ListScreenUiAction

    sealed interface SearchAction : ListScreenUiAction {
        data class OnSearchQueryChange(val query: String) : SearchAction
        object OnSearchClick : SearchAction
        object OnClearClick : SearchAction
        object OnRefresh : SearchAction
    }
}

//interface ListScreenUiActions {
//    fun onSearchQueryChange(newQuery: String)
//    fun onSearchClick()
//    fun onItemClick(sandboxItem: SandboxItem)
//    fun onItemLongClick(sandboxItem: SandboxItem)
//    fun onItemCheckChange(sandboxItem: SandboxItem, isChecked: Boolean)
//    fun onItemCloseClick(sandboxItem: SandboxItem)
//    fun onClearClick()
//}

sealed class ListUiEvent(val key: Any) {
    data class ShowToast(val message: String) : ListUiEvent(message)
    data class NavigateToDetails(val sandboxItem: SandboxItem) : ListUiEvent(sandboxItem)
}