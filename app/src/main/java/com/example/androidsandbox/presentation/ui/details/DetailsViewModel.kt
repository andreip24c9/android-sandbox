package com.example.androidsandbox.presentation.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsandbox.presentation.ui.details.navigation.itemIdArg
import com.example.androidsandbox.repository.SandboxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: SandboxRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsScreenUiState>(DetailsScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val itemId: String? = state[itemIdArg]
            itemId?.let { id ->
                val sandboxItem = repository.getSandboxItem(id)
                sandboxItem?.let { item ->
                    _uiState.emit(
                        DetailsScreenUiState.Data(
                            item.label,
                            item.description,
                            item.checked
                        )
                    )
                } ?: run {
                    _uiState.emit(DetailsScreenUiState.Error)
                }
            } ?: run {
                _uiState.emit(DetailsScreenUiState.Error)
            }
        }
    }

    fun onCheckChange(isChecked: Boolean) {
        // todo impl later
    }
}