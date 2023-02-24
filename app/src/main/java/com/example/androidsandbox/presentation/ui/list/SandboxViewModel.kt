package com.example.androidsandbox.presentation.ui.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsandbox.domain.SandboxItem
import com.example.androidsandbox.repository.SandboxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SandboxViewModel @Inject constructor(
    private val repository: SandboxRepository,
) : ViewModel(), UiEvent {
    private val _uiState = mutableStateOf(
        UiState(searchQuery = "", sandboxItems = listOf(), isLoading = true)
    )
    val uiState: UiState
        get() = _uiState.value

    private var job: Job? = null

    init {
        searchItems("", false)
    }

    override fun onSearchQueryChange(newQuery: String) {
        _uiState.value = _uiState.value.copy(searchQuery = newQuery)
        searchItems(_uiState.value.searchQuery, true)
    }

    override fun onSearchClick() {
        searchItems(_uiState.value.searchQuery, false)
    }

    override fun onItemCheckChange(sandboxItem: SandboxItem, isChecked: Boolean) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            repository.checkSandboxItem(sandboxItem.id, isChecked)?.let { updatedItem ->
                _uiState.value.sandboxItems.find { it.id == updatedItem.id }
                    ?.apply { checked = updatedItem.checked }
            } ?: run {
                // todo show error dialog
            }
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    override fun onItemCloseClick(sandboxItem: SandboxItem) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            repository.deleteSandboxItem(sandboxItem.id)?.let { deletedItem ->
                _uiState.value =
                    _uiState.value.copy(sandboxItems = _uiState.value.sandboxItems.filterNot { it == deletedItem })
            } ?: run {
                // todo handle error
            }
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    override fun onClearClick() {
        searchItems("", false)
    }

    private fun searchItems(query: String, debounce: Boolean) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(if (debounce) 500 else 0)
            _uiState.value = _uiState.value.copy(isLoading = true, searchQuery = query)
            val result = repository.fetchSandboxItems(query)
            _uiState.value = _uiState.value.copy(isLoading = false, sandboxItems = result)
        }
    }
}