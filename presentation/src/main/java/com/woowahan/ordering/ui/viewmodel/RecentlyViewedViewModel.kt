package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.Recently
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.usecase.recently.GetRecentlyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentlyViewedViewModel @Inject constructor(
    private val getRecentlyUseCase: GetRecentlyUseCase
) : ViewModel() {

    private val _recentlyViewedList = MutableStateFlow<List<Recently>>(listOf())
    val recentlyViewedList = _recentlyViewedList.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            getRecentlyUseCase().collect {
                when (it) {
                    is Result.Success -> {
                        _recentlyViewedList.emit(it.value)
                    }
                }
            }
        }
    }
}