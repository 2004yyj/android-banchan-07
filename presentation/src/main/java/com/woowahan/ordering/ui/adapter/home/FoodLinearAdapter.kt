package com.woowahan.ordering.ui.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.woowahan.ordering.databinding.ItemFoodLinearBinding
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.ui.adapter.foodDiffUtil
import com.woowahan.ordering.ui.adapter.viewholder.ItemFoodViewHolder

class FoodLinearAdapter : ListAdapter<Food, ItemFoodViewHolder.Linear>(foodDiffUtil) {
    private var onDetailClick: (String, String) -> Unit = { _, _ -> }
    private var onCartClick: (Food) -> Unit = {}

    fun setOnClick(onDetailClick: (String, String) -> Unit, onCartClick: (Food) -> Unit) {
        this.onDetailClick = onDetailClick
        this.onCartClick = onCartClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFoodViewHolder.Linear {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodLinearBinding.inflate(inflater, parent, false)
        return ItemFoodViewHolder.Linear(binding)
    }

    override fun onBindViewHolder(holder: ItemFoodViewHolder.Linear, position: Int) {
        holder.bind(getItem(position), onDetailClick, onCartClick)
    }
}