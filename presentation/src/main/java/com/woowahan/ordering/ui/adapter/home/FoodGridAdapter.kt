package com.woowahan.ordering.ui.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.woowahan.ordering.databinding.ItemFoodGridBinding
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.ui.adapter.foodDiffUtil
import com.woowahan.ordering.ui.adapter.viewholder.ItemFoodViewHolder

class FoodGridAdapter: ListAdapter<Food, ItemFoodViewHolder.Grid>(foodDiffUtil) {
    private var onClick: (String, String) -> Unit = { _, _ -> }
    fun setOnClick(onClick: (String, String) -> Unit) {
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFoodViewHolder.Grid {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodGridBinding.inflate(inflater, parent, false)
        return ItemFoodViewHolder.Grid(binding)
    }

    override fun onBindViewHolder(holder: ItemFoodViewHolder.Grid, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}