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
    private val isToolbarLoading = MutableStateFlow(false)
    private val isScreenLoading = MutableStateFlow(true)


//    private val _uiState: MutableStateFlow<SandboxScreenUiState> =
//        MutableStateFlow(SandboxScreenUiState.Default)
    //    val uiState : StateFlow<SandboxScreenUiState> = _uiState.asStateFlow()

    val uiState = combine(searchQuery, sandboxItemList, isToolbarLoading, isScreenLoading)
    { query, list, toolbarLoader, screenLoading ->
        if (screenLoading) {
            SandboxScreenUiState.Loading
        } else {
            SandboxScreenUiState.Data(
                searchQuery = query,
                sandboxItems = list,
                isLoading = toolbarLoader
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, SandboxScreenUiState.Default)

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
            isScreenLoading.value = true
            sandboxItemList.value = repository.fetchSandboxItems(searchQuery.value)
            isScreenLoading.value = false
        }
    }

    private fun searchItems(query: String, debounce: Boolean = false) {
        viewModelScope.launch {
            delay(if (debounce) 500 else 0)
            isToolbarLoading.value = true
            sandboxItemList.value = repository.fetchSandboxItems(query)
            isToolbarLoading.value = false
        }
    }

    private fun checkItem(itemId: String, isChecked: Boolean) {
        viewModelScope.launch {
            isToolbarLoading.value = true
            repository.checkSandboxItem(itemId, isChecked)?.let { updatedItem ->
                sandboxItemList.value.find { it.id == itemId }
                    ?.apply { checked = updatedItem.checked }
            } ?: run {
                //     todo why does this return null?
            }
            isToolbarLoading.value = false
        }
    }

    private fun deleteItem(itemId: String) {
        viewModelScope.launch {
            Log.d("MyLogs", "Fire item remove id: $itemId")
            isToolbarLoading.value = true
            repository.deleteSandboxItem(itemId)?.let { deletedItem ->
                Log.d("MyLogs", "received item to remove id: ${deletedItem.id}")
                sandboxItemList.value = sandboxItemList.value.filterNot { it.id == deletedItem.id }
            } ?: run {
                //    todo why null??
            }
            isToolbarLoading.value = false
        }
    }
}