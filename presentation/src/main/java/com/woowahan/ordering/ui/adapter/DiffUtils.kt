package com.woowahan.ordering.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.woowahan.ordering.domain.model.*

val foodDiffUtil = object : DiffUtil.ItemCallback<Food>() {
    override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem.detailHash == newItem.detailHash
    }
}

val bestDiffUtil = object : DiffUtil.ItemCallback<Best>() {
    override fun areItemsTheSame(oldItem: Best, newItem: Best): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Best, newItem: Best): Boolean {
        return oldItem.categoryId == newItem.categoryId
    }
}

val stringDiffUtil = object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

val cartListDiffUtil = object : DiffUtil.ItemCallback<CartListItem>() {
    override fun areItemsTheSame(oldItem: CartListItem, newItem: CartListItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CartListItem, newItem: CartListItem): Boolean {
        return if (oldItem is CartListItem.Content && newItem is CartListItem.Content) {
            oldItem.cart.isChecked == newItem.cart.isChecked
        } else if (oldItem is CartListItem.Footer && newItem is CartListItem.Footer) {
            oldItem.sum == newItem.sum
        } else {
            oldItem == newItem
        }
    }

}

val cartDiffUtil = object : DiffUtil.ItemCallback<Cart>() {
    override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
        return oldItem.id == newItem.id
    }
}

val recentlyDiffUtil = object : DiffUtil.ItemCallback<Recently>() {
    override fun areItemsTheSame(oldItem: Recently, newItem: Recently): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Recently, newItem: Recently): Boolean {
        return oldItem.detailHash == newItem.detailHash && oldItem.latestViewedTime == newItem.latestViewedTime
    }

}

val simpleOrderDiffUtil = object : DiffUtil.ItemCallback<SimpleOrder>() {
    override fun areItemsTheSame(oldItem: SimpleOrder, newItem: SimpleOrder): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SimpleOrder, newItem: SimpleOrder): Boolean {
        return oldItem.deliveryTime == newItem.deliveryTime
    }

}