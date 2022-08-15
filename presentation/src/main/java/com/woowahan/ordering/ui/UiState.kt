package com.woowahan.ordering.ui

sealed class UiState {
    object Loading : UiState()
    object Success : UiState()

    data class Error(
        val exception: String
    ) : UiState()

    object Finished : UiState()
}