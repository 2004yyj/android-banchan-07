package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.domain.model.Menu
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.usecase.food.GetMenuListUseCase
import com.woowahan.ordering.ui.fragment.home.other.kind.OtherKind
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtherDishViewModel @Inject constructor(
    private val getMenuListUseCase: GetMenuListUseCase
) : ViewModel() {

    private val _menu = MutableStateFlow<List<Food>>(emptyList())
    val menu = _menu.asStateFlow()

    fun getMenuList(kind: OtherKind) {
        val menu = when (kind) {
            OtherKind.Soup -> Menu.Soup
            OtherKind.Side -> Menu.Side
        }

        viewModelScope.launch(Dispatchers.IO) {
            getMenuListUseCase(menu).collect {
                if (it is Result.Success) {
                    _menu.emit(it.value)
                }
            }
        }
    }
}