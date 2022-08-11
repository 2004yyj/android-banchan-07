package com.woowahan.ordering.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemFoodGridBinding
import com.woowahan.ordering.databinding.ItemFoodLinearBinding
import com.woowahan.ordering.domain.model.Food

sealed class ItemFoodViewHolder(view: View): RecyclerView.ViewHolder(view) {
    class Grid(private val binding: ItemFoodGridBinding): ItemFoodViewHolder(binding.root) {
        fun bind(food: Food, onClick: (String, String) -> Unit) {
            binding.food = food
            binding.root.setOnClickListener {
                onClick(food.title, food.detailHash)
            }
        }
    }

    class Linear(private val binding: ItemFoodLinearBinding): ItemFoodViewHolder(binding.root) {
        fun bind(food: Food, onClick: (String, String) -> Unit) {
            binding.food = food
            binding.root.setOnClickListener {
                onClick(food.title, food.detailHash)
            }
        }
    }
}