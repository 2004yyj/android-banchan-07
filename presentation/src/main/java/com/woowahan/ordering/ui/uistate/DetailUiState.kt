package com.woowahan.ordering.ui.uistate

sealed class DetailUiState {
    object Success : DetailUiState()
    data class Error(
        val exception: String
    ) : DetailUiState()
    object CartExist : DetailUiState()
}