package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.History
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.usecase.history.GetHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {

    private val _historyViewedList = MutableStateFlow<List<History>>(listOf())
    val recentlyViewedList = _historyViewedList.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            getHistoryUseCase().collect {
                when (it) {
                    is Result.Success -> {
                        _historyViewedList.emit(it.value)
                    }
                }
            }
        }
    }
}