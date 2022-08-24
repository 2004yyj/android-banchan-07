package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.SimpleOrder
import com.woowahan.ordering.domain.usecase.order.GetSimpleOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel @Inject constructor(
    private val simpleOrderUseCase: GetSimpleOrderUseCase
) : ViewModel() {

    private val _simpleOrder = MutableStateFlow<List<SimpleOrder>>(emptyList())
    val simpleOrder = _simpleOrder.asStateFlow()

    fun getSimpleOrder() {
        viewModelScope.launch {
            simpleOrderUseCase().collect {
                _simpleOrder.emit(it)
            }
        }
    }
}