package com.woowahan.ordering.domain.usecase.food

import com.woowahan.ordering.domain.model.Menu
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.model.SortType
import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.FoodRepository
import kotlinx.coroutines.flow.flow

class GetMenuListUseCase(
    private val cartRepository: CartRepository,
    private val foodRepository: FoodRepository
) {
    operator fun invoke(menu: Menu, sortType: SortType) = flow {
        emit(Result.Loading)
        try {
            cartRepository.getCart().collect {
                val hashList = it.map { it.detailHash }
                val result = when (menu) {
                    is Menu.Main -> foodRepository.getMainList()
                    is Menu.Soup -> foodRepository.getSoupList()
                    is Menu.Side -> foodRepository.getSideList()
                }

                result.forEach { food ->
                    food.isAdded = hashList.contains(food.detailHash)
                }

                val sortedList = when (sortType) {
                    is SortType.Default -> {
                        result
                    }
                    is SortType.MoneyDesc -> {
                        result.sortedByDescending { it.discountedPrice }
                    }
                    is SortType.MoneyAsc -> {
                        result.sortedBy { it.discountedPrice }
                    }
                    is SortType.RateDesc -> {
                        result.sortedBy { it.discountRate }
                    }
                }
                emit(Result.Success(sortedList))
            }
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}