package com.woowahan.ordering.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.woowahan.ordering.domain.model.Best
import com.woowahan.ordering.domain.model.Food

val foodDiffUtil = object: DiffUtil.ItemCallback<Food>() {
    override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem.detailHash == newItem.detailHash
    }
}

val bestDiffUtil = object: DiffUtil.ItemCallback<Best>() {
    override fun areItemsTheSame(oldItem: Best, newItem: Best): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Best, newItem: Best): Boolean {
        return oldItem.categoryId == newItem.categoryId
    }
}

val stringDiffUtil = object: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}