package com.woowahan.ordering.ui.uistate

sealed class ListUiState<out S> {
    object Refreshing : ListUiState<Nothing>()
    class List<out S>(val list: kotlin.collections.List<S> = emptyList()) : ListUiState<S>()
    object NoInternet : ListUiState<Nothing>()
}