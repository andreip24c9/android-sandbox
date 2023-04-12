package com.example.androidsandbox.presentation.ui.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsandbox.domain.SandboxItem
import com.example.androidsandbox.repository.SandboxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: SandboxRepository,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _sandboxItemList = MutableStateFlow(listOf<SandboxItem>())
    private val _isToolbarLoading = MutableStateFlow(false)
    private val _isScreenLoading = MutableStateFlow(true)
    private val _uiEvent = MutableSharedFlow<ListUiEvent>()
    private val _searchFlow = MutableSharedFlow<ListScreenUiAction.SearchAction>()

    val uiEvent: SharedFlow<ListUiEvent> = _uiEvent.asSharedFlow()
    val uiState = combine(_searchQuery, _sandboxItemList, _isToolbarLoading, _isScreenLoading)
    { query, list, toolbarLoader, screenLoading ->
        if (screenLoading) {
            ListScreenUiState.Loading
        } else {
            ListScreenUiState.Data(
                searchQuery = query,
                sandboxItems = list,
                isLoading = toolbarLoader
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, ListScreenUiState.Default)

    init {
        viewModelScope.launch {
            _searchFlow
                .onEach { action ->
                    when (action) {
                        is ListScreenUiAction.SearchAction.OnSearchQueryChange -> {
                            _searchQuery.value = action.query
                        }
                        ListScreenUiAction.SearchAction.OnClearClick -> {
                            _searchQuery.value = ""
                        }
                        else -> {
                            // do nothing
                        }
                    }
                    if (action is ListScreenUiAction.SearchAction.OnSearchQueryChange) {
                        _searchQuery.value = action.query
                    }
                }
                .debounce { action ->
                    when (action) {
                        is ListScreenUiAction.SearchAction.OnSearchQueryChange -> 750L
                        else -> 0L
                    }
                }
                .distinctUntilChanged(areEquivalent = { old, new ->
                    when(old) {
                        is ListScreenUiAction.SearchAction.OnSearchQueryChange -> {
                            if(new is ListScreenUiAction.SearchAction.OnSearchQueryChange) {
                                old.query == new.query
                            } else {
                                old.query == _searchQuery.value
                            }
                        }
                        else -> {
                            false
                        }
                    }
                })
                .collectLatest { action ->
                    when (action) {
                        is ListScreenUiAction.SearchAction.OnRefresh -> {
                            _isScreenLoading.value = true
                        }
                        else -> {
                            _isScreenLoading.value = false
                            _isToolbarLoading.value = true
                        }
                    }
                    val result = repository.fetchSandboxItems(_searchQuery.value)
                    _isScreenLoading.value = false
                    _isToolbarLoading.value = false
                    _sandboxItemList.value = result
                }
        }

        viewModelScope.launch {
            _searchFlow.emit(ListScreenUiAction.SearchAction.OnRefresh)
        }
    }

    fun onQueryChange(newQuery: String) {
        viewModelScope.launch { _searchFlow.emit(ListScreenUiAction.SearchAction.OnSearchQueryChange(newQuery)) }
    }

    fun onSearchClick() {
        viewModelScope.launch { _searchFlow.emit(ListScreenUiAction.SearchAction.OnSearchClick) }
    }

    fun onClearClick() {
        viewModelScope.launch { _searchFlow.emit(ListScreenUiAction.SearchAction.OnClearClick) }
    }

    fun onItemClick(sandboxItem: SandboxItem) {
        viewModelScope.launch {
            _uiEvent.emit(ListUiEvent.NavigateToDetails(sandboxItem))
        }
    }

    fun onItemLongClick(sandboxItem: SandboxItem) {
        viewModelScope.launch {
            _uiEvent.emit(ListUiEvent.ShowToast("Long clicked: ${sandboxItem.label}"))
        }
    }

    fun onItemCheckChange(sandboxItem: SandboxItem, isChecked: Boolean) {
        checkItem(sandboxItem.id, isChecked)
    }

    fun onDeleteClick(sandboxItem: SandboxItem) {
        deleteItem(sandboxItem.id)
    }

    private fun checkItem(itemId: String, isChecked: Boolean) {
        viewModelScope.launch {
            _isToolbarLoading.value = true
            repository.checkSandboxItem(itemId, isChecked)?.let { updatedItem ->
                _sandboxItemList.value.find { it.id == itemId }
                    ?.apply { checked = updatedItem.checked }
            } ?: run {
                //     todo why does this return null?
            }
            _isToolbarLoading.value = false
        }
    }

    private fun deleteItem(itemId: String) {
        viewModelScope.launch {
            Log.d("MyLogs", "Fire item remove id: $itemId")
            _isToolbarLoading.value = true
            repository.deleteSandboxItem(itemId)?.let { deletedItem ->
                Log.d("MyLogs", "received item to remove id: ${deletedItem.id}")
                _sandboxItemList.value =
                    _sandboxItemList.value.filterNot { it.id == deletedItem.id }
            } ?: run {
                //    todo why null??
            }
            _isToolbarLoading.value = false
        }
    }
}