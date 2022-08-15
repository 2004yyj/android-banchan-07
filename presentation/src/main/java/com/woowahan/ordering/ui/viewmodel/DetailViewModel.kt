package com.woowahan.ordering.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.ordering.domain.model.FoodDetail
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.usecase.food.GetFoodDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getFoodDetailUseCase: GetFoodDetailUseCase
): ViewModel() {

    private val _foodDetail = MutableStateFlow<FoodDetail?>(null)
    val foodDetail = _foodDetail.asStateFlow()

    fun getFoodDetail(hash: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getFoodDetailUseCase(hash).collect {
                if (it is Result.Success) {
                    _foodDetail.emit(it.value)
                }
            }
        }
    }
}
