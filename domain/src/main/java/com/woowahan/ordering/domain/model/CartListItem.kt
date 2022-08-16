package com.woowahan.ordering.domain.model

sealed class CartListItem {
    data class Header(
        val isSelectedAll: Boolean
    ) : CartListItem()

    data class Content(
        val cart: Cart
    ) : CartListItem()

    data class Footer(
        val sum: Long,
        val deliveryFee: Int,
        val insufficientAmount: Int,
        val enableToOrder: Boolean
    ) : CartListItem()
}
