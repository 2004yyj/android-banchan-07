package com.woowahan.ordering.domain.usecase.food

import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.FoodRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetFoodDetailUseCase(
    private val foodRepository: FoodRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(hash: String) = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(foodRepository.getFoodDetail(hash)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(ioDispatcher)
}