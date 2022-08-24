package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.Best
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.usecase.food.GetBestListUseCase
import com.woowahan.ordering.network.ConnectionInterceptor
import com.woowahan.ordering.ui.uistate.ListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BestViewModel @Inject constructor(
    private val getBestListUseCase: GetBestListUseCase
) : ViewModel() {

    private lateinit var bestListJob: Job

    private val _uiState = MutableStateFlow<ListUiState<Best>>(ListUiState.List())
    val uiState = _uiState.asStateFlow()

    fun getBestList() {
        if (this::bestListJob.isInitialized)
            bestListJob.cancel()

        bestListJob = viewModelScope.launch {
            getBestListUseCase().collect {
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