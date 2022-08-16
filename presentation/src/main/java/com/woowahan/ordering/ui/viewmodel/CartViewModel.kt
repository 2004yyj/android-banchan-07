package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.*
import com.woowahan.ordering.domain.usecase.cart.*
import com.woowahan.ordering.domain.usecase.order.InsertOrderUseCase
import com.woowahan.ordering.domain.usecase.recently.GetSimpleRecentlyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val deleteAllSelectedCartUseCase: DeleteAllSelectedCartUseCase,
    private val selectAllCartUseCase: SelectAllCartUseCase,
    private val insertOrderUseCase: InsertOrderUseCase,
    private val getSimpleRecentlyUseCase: GetSimpleRecentlyUseCase
) : ViewModel() {

    private val _cartList = MutableStateFlow<List<CartListItem>>(listOf())
    val cartList = _cartList.asStateFlow()

    private val _isSelectedAll = MutableStateFlow(true)
    val isSelectedAll = _isSelectedAll.asStateFlow()

    private val _recentlyList = MutableStateFlow<List<Recently>>(listOf())
    val recentlyList = _recentlyList.asStateFlow()

    private val _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()

    init {
        getAllCart()
        getRecently()
    }

    fun getAllCart() {
        viewModelScope.launch(Dispatchers.IO) {
            getCartUseCase().collect { result ->
                when (result) {
                    is Result.Success -> {
                        val list = result.value
                        val checkedList = list.filter { it.isChecked }

                        if (list.size != checkedList.size) {
                            _isSelectedAll.emit(false)
                        } else {
                            _isSelectedAll.emit(true)
                        }

                        val sum = checkedList.sumOf { it.price * it.count }

                        val deliveryFee =
                            if (sum >= DELIVERY_FREE_LIMIT) 0 else DEFAULT_DELIVERY_FEE
                        val insufficientAmount =
                            if (sum >= DELIVERY_FREE_LIMIT) 0 else DELIVERY_FREE_LIMIT - sum

                        val mergedList = mutableListOf<CartListItem>().apply {
                            addAll(list.map { CartListItem.Content(it) })
                            add(0, CartListItem.Header(isSelectedAll.value))
                            add(
                                CartListItem.Footer(
                                    sum = sum,
                                    deliveryFee = deliveryFee,
                                    insufficientAmount = insufficientAmount.toInt(),
                                    enableToOrder = sum >= ORDER_MINIMUM_AMOUNT
                                )
                            )
                        }
                        _cartList.emit(mergedList)
                    }
                }
            }
        }
    }

    fun getRecently() {
        viewModelScope.launch(Dispatchers.IO) {
            getSimpleRecentlyUseCase().collect {
                when (it) {
                    is Result.Success -> {
                        _recentlyList.emit(it.value)
                    }
                }
            }
        }
    }

    fun selectAll() {
        viewModelScope.launch(Dispatchers.IO) {
            selectAllCartUseCase(isSelectedAll.value.not()).collect {

            }
        }
    }

    fun checkItemClick(cart: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            updateCartUseCase(cart.copy(isChecked = !cart.isChecked)).collect {

            }
        }
    }

    fun minusItemClick(cart: Cart) {
        if (cart.count > 1) {
            viewModelScope.launch(Dispatchers.IO) {
                updateCartUseCase(cart.copy(count = cart.count - 1)).collect {
                    if (it is Result.Success) {
                    }
                }
            }
        }
    }

    fun plusItemClick(cart: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            updateCartUseCase(cart.copy(count = cart.count + 1)).collect {
                if (it is Result.Success) {
                }
            }

        }
    }

    fun deleteItemClick(cart: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCartUseCase(cart).collect()
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllSelectedCartUseCase().collect()
        }
    }

    fun orderClick() {
        val order = Order(id = 0, deliveryTime = System.currentTimeMillis())
        viewModelScope.launch(Dispatchers.IO) {
            insertOrderUseCase(order).collect {
                // TODO
            }
        }
    }

    companion object {
        const val DELIVERY_FREE_LIMIT = 40000
        const val DEFAULT_DELIVERY_FEE = 2500
        const val ORDER_MINIMUM_AMOUNT = 10000
    }
}