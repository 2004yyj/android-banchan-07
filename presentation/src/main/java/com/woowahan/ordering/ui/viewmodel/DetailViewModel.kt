package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.FoodDetail
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.usecase.cart.InsertCartUseCase
import com.woowahan.ordering.domain.usecase.food.GetFoodDetailUseCase
import com.woowahan.ordering.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getFoodDetailUseCase: GetFoodDetailUseCase,
    private val insertCartUseCase: InsertCartUseCase
): ViewModel() {

    private val _uiState = MutableSharedFlow<UiState>()
    val uiState = _uiState.asSharedFlow()

    private val _foodDetail = MutableStateFlow<FoodDetail?>(null)
    val foodDetail = _foodDetail.asStateFlow()

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

    fun getFoodDetail(hash: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getFoodDetailUseCase(hash).collect {
                if (it is Result.Success) {
                    _foodDetail.emit(it.value)
                }
            }
        }
    }

    fun addToCart(title: String, food: FoodDetail) {
        val cart = Cart(
            id = 0,
            title = title,
            thumbnail = food.topImage,
            price = food.discountedPrice,
            count = _count.value,
            detailHash = food.hash
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
