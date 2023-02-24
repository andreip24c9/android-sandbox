package com.example.androidsandbox.presentation.ui.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsandbox.domain.SandboxItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SandboxViewModel : ViewModel(), UiEvent {
    private val _uiState = mutableStateOf(
        UiState(searchQuery = "", sandboxItems = getSandboxItems(), isLoading = false)
    )
    val uiState: UiState
        get() = _uiState.value

    private var job: Job? = null

    override fun onSearchQueryChange(newQuery: String) {
        _uiState.value = _uiState.value.copy(searchQuery = newQuery)
        searchItems(_uiState.value.searchQuery, true)
    }

    override fun onSearchClick() {
        searchItems(_uiState.value.searchQuery, false)
    }

    override fun onItemCheckChange(task: SandboxItem, checked: Boolean) {
        _uiState.value.sandboxItems.find { it.id == task.id }?.let { it.checked = checked }
    }

    override fun onItemCloseClick(task: SandboxItem) {
        _uiState.value =
            _uiState.value.copy(sandboxItems = _uiState.value.sandboxItems.filterNot { it == task })
    }

    override fun onClearClick() {
        searchItems("", false)
    }

    private fun searchItems(query: String, debounce: Boolean) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(if (debounce) 500 else 0)
            _uiState.value = _uiState.value.copy(isLoading = true, searchQuery = query)
            val result = searchItemRequest(query)
            _uiState.value = _uiState.value.copy(isLoading = false, sandboxItems = result)
        }
    }

    private suspend fun searchItemRequest(query: String): List<SandboxItem> {
        delay(1000) // simulates network request
        return getSandboxItems()
            .takeIf { query.isNotEmpty() }
            ?.filter { it.label.contains(query, ignoreCase = true) }
            ?: getSandboxItems()
    }
}

private fun getSandboxItems() = List(30) { SandboxItem(it, "Item ${it + 1}", false) }
