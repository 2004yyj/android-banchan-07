package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.FoodDetail
import com.woowahan.ordering.domain.model.History
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.usecase.cart.ExistOrderedCartException
import com.woowahan.ordering.domain.usecase.cart.InsertCartUseCase
import com.woowahan.ordering.domain.usecase.food.GetFoodDetailUseCase
import com.woowahan.ordering.domain.usecase.history.InsertHistoryUseCase
import com.woowahan.ordering.ui.uistate.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val insertHistoryUseCase: InsertHistoryUseCase,
    private val getFoodDetailUseCase: GetFoodDetailUseCase,
    private val insertCartUseCase: InsertCartUseCase
) : ViewModel() {

    private val _uiState = MutableSharedFlow<DetailUiState>()
    val uiState = _uiState.asSharedFlow()

    private val _foodDetail = MutableStateFlow<FoodDetail?>(null)
    val foodDetail = _foodDetail.asStateFlow()

    private var _count = MutableStateFlow(1)
    val count = _count.asStateFlow()

    fun init() {
        _count.value = 1
    }

    fun increaseCount() {
        _count.value = _count.value + 1
    }

    fun decreaseCount() {
        if (count.value > 1) {
            _count.value = _count.value - 1
        }
    }

    fun insertRecently(title: String, foodDetail: FoodDetail) {
        val history = History(
            detailHash = foodDetail.hash,
            title = title,
            thumbnail = foodDetail.thumbImages[0],
            price = foodDetail.price,
            discountedPrice = foodDetail.discountedPrice,
            System.currentTimeMillis()
        )

        viewModelScope.launch {
            insertHistoryUseCase(history).collect {}
        }
    }

    fun getFoodDetail(hash: String) {
        viewModelScope.launch {
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
            thumbnail = food.thumbImages[0],
            discountedPrice = food.discountedPrice,
            count = _count.value,
            detailHash = food.hash
        )

        viewModelScope.launch {
            insertCartUseCase(cart).collect {
                when (it) {
                    is Result.Success -> {
                        _uiState.emit(DetailUiState.Success)
                        _foodDetail.update { foodDetail ->
                            foodDetail?.copy(isCarted = true)
                        }
                    }
                    is Result.Failure -> {
                        when (it.cause) {
                            is ExistOrderedCartException -> {
                                _uiState.emit(DetailUiState.CartExist)
                            }
                            else -> _uiState.emit(DetailUiState.Error(it.cause.toString()))
                        }
                    }
                }
            }
        }
    }
}