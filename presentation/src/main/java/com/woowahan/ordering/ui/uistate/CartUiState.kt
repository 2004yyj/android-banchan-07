package com.woowahan.ordering.ui.uistate

sealed class CartUiState {
    object Loading : CartUiState()
    object Success : CartUiState()

    data class SuccessOrder(
        val deliveryTime: Long,
        val title: String,
        val count: Int
    ) : CartUiState()

    data class Error(
        val message: String
    ) : CartUiState()
}