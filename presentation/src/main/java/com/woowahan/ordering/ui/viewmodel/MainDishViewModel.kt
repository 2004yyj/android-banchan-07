package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.domain.model.Menu
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.model.SortType
import com.woowahan.ordering.domain.usecase.food.GetMenuListUseCase
import com.woowahan.ordering.ui.uistate.ListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainDishViewModel @Inject constructor(
    private val getMenuListUseCase: GetMenuListUseCase
) : ViewModel() {

    var sortType: SortType = SortType.Default

    private lateinit var mainListJob: Job

    private val _menu = MutableStateFlow<ListUiState<Food>>(ListUiState.List())
    val menu = _menu.asStateFlow()

    fun getMenuList(menu: Menu) {
        if (this::mainListJob.isInitialized)
            mainListJob.cancel()

        mainListJob = viewModelScope.launch(Dispatchers.IO) {
            getMenuListUseCase(menu, sortType).collect {
                when (it) {
                    is Result.Loading -> {
                        if (this@MainDishViewModel::mainListJob.isInitialized)
                            _menu.emit(ListUiState.Refreshing)
                    }
                    is Result.Success -> _menu.emit(ListUiState.List(it.value))
                }
            }
        }
    }
}