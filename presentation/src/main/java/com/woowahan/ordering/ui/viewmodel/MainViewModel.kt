package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.usecase.cart.GetCartUseCase
import com.woowahan.ordering.domain.usecase.order.IsExistNotDeliveredOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val isExistsNotDeliveredOrderUseCase: IsExistNotDeliveredOrderUseCase
): ViewModel() {

    private val _cartSize = MutableStateFlow(0)
    val cartSize = _cartSize.asStateFlow()

    private val _isExistsNotDelivered = MutableStateFlow(false)
    val isExistsNotDelivered = _isExistsNotDelivered.asStateFlow()

    fun getCartSize() {
        viewModelScope.launch(Dispatchers.IO) {
            getCartUseCase().collect {
                if (it is Result.Success) {
                    _cartSize.emit(it.value.size)
                }
            }
        }
    }

    fun isExistsNotDeliveredOrder() {
        viewModelScope.launch {
            isExistsNotDeliveredOrderUseCase().collect {
                _isExistsNotDelivered.emit(it)
            }
        }
    }
}