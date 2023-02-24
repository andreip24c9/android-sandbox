package com.example.androidsandbox.presentation.ui.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsandbox.domain.SandboxItem
import com.example.androidsandbox.repository.SandboxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SandboxViewModel @Inject constructor(
    private val repository: SandboxRepository,
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val sandboxItemList = MutableStateFlow(listOf<SandboxItem>())
    private val showToolbarLoader = MutableStateFlow(false)
    private val isLoading = MutableStateFlow(false)
    val uiStateFlow: StateFlow<SandboxScreenUiState> =
        combine(
            searchQuery,
            sandboxItemList,
            showToolbarLoader,
            isLoading
        ) { query, list, toolbarLoader, screenLoading ->
            Log.d(
                "MyLogs",
                "combine triggered: $query, ${list.size}, toolbarLoader=$toolbarLoader, screenLoader=$screenLoading"
            )
            if (screenLoading) {
                SandboxScreenUiState.Loading
            } else {
                SandboxScreenUiState.Data(
                    searchQuery = query,
                    sandboxItems = list,
                    isLoading = toolbarLoader
                )
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, SandboxScreenUiState.Loading)

    val uiEvents = object : SandboxScreenUiEvents {
        override fun onSearchQueryChange(newQuery: String) {
            searchQuery.value = newQuery
        }

        override fun onSearchClick() {
            searchItems(searchQuery.value)
        }

        override fun onItemCheckChange(sandboxItem: SandboxItem, isChecked: Boolean) {
            checkItem(sandboxItem.id, isChecked)
        }

        override fun onItemCloseClick(sandboxItem: SandboxItem) {
            deleteItem(sandboxItem.id)
        }

        override fun onClearClick() {
            searchQuery.value = ""
            searchItems(searchQuery.value)
        }
    }

    init {
        viewModelScope.launch {
            isLoading.value = true
            sandboxItemList.value = repository.fetchSandboxItems(searchQuery.value)
            isLoading.value = false
        }
    }

    private fun searchItems(query: String, debounce: Boolean = false) {
        viewModelScope.launch {
            delay(if (debounce) 500 else 0)
            showToolbarLoader.value = true
            sandboxItemList.value = repository.fetchSandboxItems(query)
            showToolbarLoader.value = false
        }
    }

    private fun checkItem(itemId: String, isChecked: Boolean) {
        viewModelScope.launch {
            showToolbarLoader.value = true
            repository.checkSandboxItem(itemId, isChecked)?.let { updatedItem ->
                sandboxItemList.value.find { it.id == itemId }
                    ?.apply { checked = updatedItem.checked }
            } ?: run {
                //     todo why does this return null?
            }
            showToolbarLoader.value = false
        }
    }

    private fun deleteItem(itemId: String) {
        viewModelScope.launch {
            Log.d("MyLogs", "Fire item remove id: $itemId")
            showToolbarLoader.value = true
            repository.deleteSandboxItem(itemId)?.let { deletedItem ->
                Log.d("MyLogs", "received item to remove id: ${deletedItem.id}")
                sandboxItemList.value = sandboxItemList.value.filterNot { it.id == deletedItem.id }
            } ?: run {
                //    todo why null??
            }
            showToolbarLoader.value = false
        }
    }
}