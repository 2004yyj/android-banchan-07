package com.woowahan.ordering.ui.uistate

sealed class CartBottomSheetUiState {
    object Loading : CartBottomSheetUiState()
    object Success : CartBottomSheetUiState()

    data class Error(
        val exception: String
    ) : CartBottomSheetUiState()

    object Finished : CartBottomSheetUiState()
}