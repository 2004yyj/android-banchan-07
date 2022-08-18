package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.OrderedCartList
import com.woowahan.ordering.domain.usecase.order.GetOrderedCartByDeliveryTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val getOrderedCartByDeliveryTimeUseCase: GetOrderedCartByDeliveryTimeUseCase
) : ViewModel() {
    private val _orderedCartList = MutableSharedFlow<OrderedCartList>()
    val orderedCartList = _orderedCartList.asSharedFlow()

    fun getOrderedCart(deliveryTime: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getOrderedCartByDeliveryTimeUseCase(deliveryTime).collect {
                _orderedCartList.emit(it.copy())
            }
        }
    }
}