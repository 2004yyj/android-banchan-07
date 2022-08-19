package com.woowahan.ordering.ui.fragment.cart

import com.woowahan.ordering.domain.model.Cart

sealed class CartListItem {
    data class Header(
        val isSelectedAll: Boolean
    ) : CartListItem()

    data class Content(
        val cart: Cart
    ) : CartListItem()

    data class Footer(
        val title: String,
        val count: Int,
        val sum: Long,
        val deliveryFee: Int,
        val insufficientAmount: Int,
        val enableToOrder: Boolean
    ) : CartListItem()

    object Empty : CartListItem()
}
