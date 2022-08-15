package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.usecase.cart.InsertCartUseCase
import com.woowahan.ordering.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartBottomSheetViewModel @Inject constructor(
    private val insertCartUseCase: InsertCartUseCase
) : ViewModel() {

    private val _uiState = MutableSharedFlow<UiState>()
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

    fun cancel() = viewModelScope.launch {
        _uiState.emit(UiState.Finished)
    }

    fun addToCart(food: Food) {
        val cart = Cart(
            id = 0,
            title = food.title,
            thumbnail = food.image,
            price = food.discountedPrice,
            count = _count.value,
            detailHash = food.detailHash
        )

        viewModelScope.launch(Dispatchers.IO) {
            insertCartUseCase(cart).collect {
                when (it) {
                    is Result.Loading -> {
                        _uiState.emit(UiState.Loading)
                    }
                    is Result.Success -> {
                        _uiState.emit(UiState.Success)
                    }
                    is Result.Failure -> {
                        _uiState.emit(UiState.Error(it.cause.toString()))
                    }
                }
            }
        }
    }
}
