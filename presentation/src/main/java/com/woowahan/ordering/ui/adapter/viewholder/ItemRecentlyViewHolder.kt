package com.woowahan.ordering.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.data.mapper.toFoodModel
import com.woowahan.ordering.databinding.*
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.domain.model.Recently

sealed class ItemRecentlyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(
        recently: Recently,
        onDetailClick: (String, String) -> Unit,
        onCartClick: (Food) -> Unit
    )

    class Grid(private val binding: ItemRecentlyGridBinding) : ItemRecentlyViewHolder(binding.root) {
        override fun bind(
            recently: Recently,
            onDetailClick: (String, String) -> Unit,
            onCartClick: (Food) -> Unit
        ) {
            binding.recently = recently
            binding.root.setOnClickListener {
                onDetailClick(recently.title, recently.detailHash)
            }
            binding.btnAddCart.setOnClickListener {
                onCartClick(recently.toFoodModel())
            }
        }
    }

    class Horizontal(private val binding: ItemRecentlyHorizontalBinding) : ItemRecentlyViewHolder(binding.root) {
        override fun bind(
            recently: Recently,
            onDetailClick: (String, String) -> Unit,
            onCartClick: (Food) -> Unit
        ) {
            binding.recently = recently
            binding.root.setOnClickListener {
                onDetailClick(recently.title, recently.detailHash)
            }
        }
    }
}