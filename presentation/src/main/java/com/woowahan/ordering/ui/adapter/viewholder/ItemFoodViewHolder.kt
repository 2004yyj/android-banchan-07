package com.woowahan.ordering.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemFoodGridBinding
import com.woowahan.ordering.databinding.ItemFoodLinearBinding
import com.woowahan.ordering.domain.model.Food

sealed class ItemFoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(
        food: Food,
        onDetailClick: (String, String) -> Unit,
        onCartClick: (Food) -> Unit
    )

    class Grid(private val binding: ItemFoodGridBinding) : ItemFoodViewHolder(binding.root) {
        override fun bind(
            food: Food,
            onDetailClick: (String, String) -> Unit,
            onCartClick: (Food) -> Unit
        ) {
            binding.food = food
            binding.root.setOnClickListener {
                onDetailClick(food.title, food.detailHash)
            }
            binding.btnAddCart.setOnClickListener {
                onCartClick(food)
            }
        }

    }

    class Linear(private val binding: ItemFoodLinearBinding) : ItemFoodViewHolder(binding.root) {
        override fun bind(
            food: Food,
            onDetailClick: (String, String) -> Unit,
            onCartClick: (Food) -> Unit
        ) {
            binding.food = food
            binding.root.setOnClickListener {
                onDetailClick(food.title, food.detailHash)
            }
            binding.btnAddCart.setOnClickListener {
                onCartClick(food)
            }
        }
    }
}