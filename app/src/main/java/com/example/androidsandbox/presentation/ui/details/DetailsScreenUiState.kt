package com.example.androidsandbox.presentation.ui.details

sealed class DetailsScreenUiState {
    data class Data(val title: String, val checked: Boolean) : DetailsScreenUiState()
    object Loading : DetailsScreenUiState()
}

interface DetailsScreenUiActions {
    fun onNavigateBackClick()
    fun onCheckChange(checked: Boolean)
}

sealed class DetailsScreenUiEvent {

}