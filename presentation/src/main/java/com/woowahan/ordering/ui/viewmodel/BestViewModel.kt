package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.Best
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.usecase.food.GetBestListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BestViewModel @Inject constructor(
    private val getBestListUseCase: GetBestListUseCase
): ViewModel() {

    private lateinit var bestListJob: Job

    private val _best = MutableStateFlow<List<Best>>(emptyList())
    val best = _best.asStateFlow()

    fun getBestList() {
        if (this::bestListJob.isInitialized)
            bestListJob.cancel()

        bestListJob = viewModelScope.launch(Dispatchers.IO) {
            getBestListUseCase().collect {
                if (it is Result.Success) {
                    _best.emit(it.value)
                }
            }
        }
    }

}