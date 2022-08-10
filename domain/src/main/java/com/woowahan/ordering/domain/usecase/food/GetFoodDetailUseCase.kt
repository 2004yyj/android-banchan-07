package com.woowahan.ordering.domain.usecase.food

import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.FoodRepository
import kotlinx.coroutines.flow.flow

class GetFoodDetailUseCase(
    private val foodRepository: FoodRepository
) {
    operator fun invoke(hash: String) = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(foodRepository.getFoodDetail(hash)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}