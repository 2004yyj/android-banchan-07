package com.woowahan.ordering.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemFoodGridBinding
import com.woowahan.ordering.databinding.ItemFoodHorizontalBinding
import com.woowahan.ordering.databinding.ItemFoodVerticalBinding
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.ui.listener.setOnThrottleClickListener

sealed class ItemFoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(food: Food)

    class Grid(
        private val binding: ItemFoodGridBinding,
        private val onDetailClick: (String, String) -> Unit,
        private val onCartClick: (Food) -> Unit
    ) : ItemFoodViewHolder(binding.root) {
        override fun bind(food: Food) = with(binding) {
            this.food = food
            root.setOnClickListener {
                onDetailClick(food.title, food.detailHash)
            }
            btnAddCart.setOnThrottleClickListener {
                onCartClick(food)
            }
        }
    }

    class Vertical(
        private val binding: ItemFoodVerticalBinding,
        private val onDetailClick: (String, String) -> Unit,
        private val onCartClick: (Food) -> Unit
    ) : ItemFoodViewHolder(binding.root) {
        override fun bind(food: Food) = with(binding) {
            this.food = food
            root.setOnClickListener {
                onDetailClick(food.title, food.detailHash)
            }
            btnAddCart.setOnThrottleClickListener {
                onCartClick(food)
            }
        }
    }

    class Horizontal(
        private val binding: ItemFoodHorizontalBinding,
        private val onDetailClick: (String, String) -> Unit,
        private val onCartClick: (Food) -> Unit
    ) : ItemFoodViewHolder(binding.root) {
        override fun bind(food: Food) = with(binding) {
            this.food = food
            root.setOnClickListener {
                onDetailClick(food.title, food.detailHash)
            }
            btnAddCart.setOnThrottleClickListener {
                onCartClick(food)
            }
        }
    }
}