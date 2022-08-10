package com.woowahan.ordering.domain.usecase.food

import com.woowahan.ordering.domain.model.Menu
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.FoodRepository
import kotlinx.coroutines.flow.flow

class GetMenuListUseCase(
    private val cartRepository: CartRepository,
    private val foodRepository: FoodRepository
) {
    operator fun invoke(menu: Menu) = flow {
        emit(Result.Loading)
        try {
            val cartList = cartRepository.getCart().map { it.detailHash }

            val result = when (menu) {
                is Menu.Main -> foodRepository.getMainList()
                is Menu.Soup -> foodRepository.getSoupList()
                is Menu.Side -> foodRepository.getSideList()
            }.forEach { food ->
                food.isAdded = cartList.contains(food.detailHash)
            }

            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}