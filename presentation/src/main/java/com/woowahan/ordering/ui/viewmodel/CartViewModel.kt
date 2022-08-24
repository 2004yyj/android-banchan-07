package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.CartResult
import com.woowahan.ordering.domain.model.Order
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.usecase.cart.*
import com.woowahan.ordering.domain.usecase.order.InsertOrderUseCase
import com.woowahan.ordering.ui.fragment.cart.CartListItem
import com.woowahan.ordering.ui.uistate.CartUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartResultUseCase: GetCartResultUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val deleteAllSelectedCartUseCase: DeleteAllSelectedCartUseCase,
    private val selectAllCartUseCase: SelectAllCartUseCase,
    private val insertOrderUseCase: InsertOrderUseCase
) : ViewModel() {

    private val _cartList = MutableStateFlow<List<CartListItem>>(listOf())
    val cartList = _cartList.asStateFlow()

    private val _isSelectedAll = MutableStateFlow(true)

    private val _uiState = MutableSharedFlow<CartUiState>()
    val uiState = _uiState.asSharedFlow()

    init {
        getAllCart()
    }

    private fun getAllCart() {
        viewModelScope.launch(Dispatchers.IO) {
            getCartResultUseCase().collect {
                when (it) {
                    is Result.Loading -> {
                        _uiState.emit(CartUiState.Loading)
                    }
                    is Result.Failure -> {
                        _uiState.emit(CartUiState.Error(it.cause.message.toString()))
                    }
                    is Result.Success -> {
                        makeMergedList(it.value)
                    }
                }
            }
        }
    }

    private suspend fun makeMergedList(result: CartResult) {
        _isSelectedAll.emit(result.isSelectedAll)

        if (result.list.isEmpty()) {
            _cartList.emit(
                listOf(
                    CartListItem.Empty,
                    CartListItem.CartHistory(result.historyList)
                )
            )
        } else {
            val mergedList = mutableListOf<CartListItem>().apply {
                addAll(result.list.map { CartListItem.Content(it) })
                add(0, CartListItem.Header(result.isSelectedAll))
                add(
                    CartListItem.Footer(
                        title = result.title,
                        count = result.count,
                        sum = result.sum,
                        deliveryFee = result.deliveryFee,
                        insufficientAmount = result.insufficientAmount,
                        enableToOrder = result.enableToOrder
                    )
                )
                add(CartListItem.CartHistory(result.historyList))
            }
            _cartList.emit(mergedList)
        }
    }

    fun selectAll() {
        viewModelScope.launch(Dispatchers.IO) {
            selectAllCartUseCase(_isSelectedAll.value.not()).collect {
                handleUpdateEvent(it)
            }
        }
    }

    fun checkItemClick(cart: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            updateCartUseCase(cart.copy(isChecked = !cart.isChecked)).collect {
                handleUpdateEvent(it)
            }
        }
    }

    fun minusItemClick(cart: Cart) {
        if (cart.count > 1) {
            viewModelScope.launch(Dispatchers.IO) {
                updateCartUseCase(cart.copy(count = cart.count - 1)).collect {
                    handleUpdateEvent(it)
                }
            }
        }
    }

    fun plusItemClick(cart: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            updateCartUseCase(cart.copy(count = cart.count + 1)).collect {
                handleUpdateEvent(it)
            }
        }
    }

    fun deleteItemClick(cart: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCartUseCase(cart).collect {
                handleUpdateEvent(it)
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllSelectedCartUseCase().collect {
                handleUpdateEvent(it)
            }
        }
    }

    fun orderClick(deliveryTime: Long, title: String, count: Int) {
        val order = Order(id = 0, deliveryTime = deliveryTime, isDelivered = false)
        viewModelScope.launch(Dispatchers.IO) {
            insertOrderUseCase(order).collect {
                when (it) {
                    is Result.Success -> {
                        _uiState.emit(
                            CartUiState.SuccessOrder(
                                deliveryTime = deliveryTime,
                                title = title,
                                count = count
                            )
                        )
                    }
                    is Result.Failure -> {
                        _uiState.emit(CartUiState.Error(it.cause.toString()))
                    }
                }
            }
        }
    }

    private suspend fun handleUpdateEvent(result: Result<*>) {
        when (result) {
            is Result.Success -> {
                _uiState.emit(CartUiState.Success)
            }
            is Result.Failure -> {
                _uiState.emit(CartUiState.Error(result.cause.toString()))
            }
        }
    }
}