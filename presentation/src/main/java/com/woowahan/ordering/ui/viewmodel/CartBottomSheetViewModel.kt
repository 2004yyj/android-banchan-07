package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.usecase.cart.InsertCartUseCase
import com.woowahan.ordering.ui.uistate.CartBottomSheetUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartBottomSheetViewModel @Inject constructor(
    private val insertCartUseCase: InsertCartUseCase
) : ViewModel() {

    private val _uiState = MutableSharedFlow<CartBottomSheetUiState>()
    val uiState = _uiState.asSharedFlow()

    private var _count = MutableStateFlow(1)
    val count = _count.asStateFlow()

    fun increaseCount() {
        _count.value = _count.value + 1
    }

    fun decreaseCount() {
        if (count.value > 1) {
            _count.value = _count.value - 1
        }
    }

    fun cancel() {
        viewModelScope.launch {
            _uiState.emit(CartBottomSheetUiState.Finished)
        }
    }

    fun addToCart(cart: Cart) {
        viewModelScope.launch {
            insertCartUseCase(cart.copy(count = _count.value)).collect {
                when (it) {
                    is Result.Success -> {
                        _uiState.emit(CartBottomSheetUiState.Success)
                    }
                    is Result.Failure -> {
                        _uiState.emit(CartBottomSheetUiState.Error(it.cause.toString()))
                    }
                }
            }
        }
    }
}
