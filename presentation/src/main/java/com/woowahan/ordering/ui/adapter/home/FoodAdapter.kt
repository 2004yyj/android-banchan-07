package com.woowahan.ordering.ui.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.woowahan.ordering.databinding.ItemFoodGridBinding
import com.woowahan.ordering.databinding.ItemFoodHorizontalBinding
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.ui.adapter.foodDiffUtil
import com.woowahan.ordering.ui.adapter.viewholder.ItemFoodViewHolder

class FoodAdapter : ListAdapter<Food, ItemFoodViewHolder>(foodDiffUtil) {
    private var itemViewType: FoodItemViewType = FoodItemViewType.GridItem

    private var onDetailClick: (String, String) -> Unit = { _, _ -> }
    private var onCartClick: (Food) -> Unit = {}

    fun viewTypeChange(itemViewType: FoodItemViewType) {
        this.itemViewType = itemViewType
        notifyDataSetChanged()
    }

    fun setOnClick(onDetailClick: (String, String) -> Unit, onCartClick: (Food) -> Unit) {
        this.onDetailClick = onDetailClick
        this.onCartClick = onCartClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            FoodItemViewType.GridItem.ordinal -> {
                val binding = ItemFoodGridBinding.inflate(inflater, parent, false)
                ItemFoodViewHolder.Grid(binding)
            }
            FoodItemViewType.VerticalItem.ordinal -> {
                val binding = ItemFoodVerticalBinding.inflate(inflater, parent, false)
                ItemFoodViewHolder.Vertical(binding)
            }
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    override fun onBindViewHolder(holder: ItemFoodViewHolder, position: Int) {
        holder.bind(getItem(position), onDetailClick, onCartClick)
    }

    override fun getItemViewType(position: Int): Int {
        return itemViewType.ordinal
    }

    enum class FoodItemViewType {
        GridItem, VerticalItem, HorizontalItem
    }
}

