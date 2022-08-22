package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.domain.model.Menu
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.model.SortType
import com.woowahan.ordering.domain.usecase.food.GetMenuListUseCase
import com.woowahan.ordering.ui.fragment.home.other.kind.OtherKind
import com.woowahan.ordering.ui.uistate.ListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtherDishViewModel @Inject constructor(
    private val getMenuListUseCase: GetMenuListUseCase
) : ViewModel() {

    private lateinit var otherListJob: Job

    private val _menu = MutableStateFlow<ListUiState<Food>>(ListUiState.List())
    val menu = _menu.asStateFlow()

    fun getMenuList(kind: OtherKind, sortType: SortType = SortType.Default) {
        val menu = when (kind) {
            OtherKind.Soup -> Menu.Soup
            OtherKind.Side -> Menu.Side
        }

        if (this::otherListJob.isInitialized)
            otherListJob.cancel()

        otherListJob = viewModelScope.launch(Dispatchers.IO) {
            getMenuListUseCase(menu, sortType).collect {
                when (it) {
                    is Result.Loading -> {
                        if (this@OtherDishViewModel::otherListJob.isInitialized)
                            _menu.emit(ListUiState.Refreshing)
                    }
                    is Result.Success -> _menu.emit(ListUiState.List(it.value))
                }
            }
        }
    }
}