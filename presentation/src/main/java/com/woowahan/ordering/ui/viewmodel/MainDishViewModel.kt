package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.domain.model.Menu
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.model.SortType
import com.woowahan.ordering.domain.usecase.food.GetMenuListUseCase
import com.woowahan.ordering.network.ConnectionInterceptor
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

    private val _uiState = MutableStateFlow<ListUiState<Food>>(ListUiState.List())
    val uiState = _uiState.asStateFlow()

    fun getMenuList() {
        if (this::mainListJob.isInitialized)
            mainListJob.cancel()

        mainListJob = viewModelScope.launch(Dispatchers.IO) {
            getMenuListUseCase(Menu.Main, sortType).collect {
                when (it) {
                    is Result.Loading -> _uiState.emit(ListUiState.Refreshing)
                    is Result.Success -> _uiState.emit(ListUiState.List(it.value))
                    is Result.Failure -> {
                        when (it.cause) {
                            is ConnectionInterceptor.NoInternetConnectionException -> {
                                _uiState.emit(ListUiState.NoInternet)
                            }
                        }
                    }
                }
            }
        }
    }
}