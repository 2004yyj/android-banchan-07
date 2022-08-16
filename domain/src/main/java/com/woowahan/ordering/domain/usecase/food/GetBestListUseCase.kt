package com.woowahan.ordering.domain.usecase.food

import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.FoodRepository
import kotlinx.coroutines.flow.flow

class GetBestListUseCase(
    private val cartRepository: CartRepository,
    private val foodRepository: FoodRepository
) {
    operator fun invoke() = flow {
        emit(Result.Loading)
        try {
            cartRepository.getCart().collect {
                val hashList = it.map { cart -> cart.detailHash }
                val bestList = foodRepository.getBestList()

                bestList.forEach {
                    it.items.forEach { food ->
                        food.isAdded = hashList.contains(food.detailHash)
                    }
                }
                emit(Result.Success(bestList))
            }
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}