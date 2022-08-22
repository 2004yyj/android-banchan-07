package com.woowahan.ordering.ui.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.woowahan.ordering.databinding.ItemFoodGridBinding
import com.woowahan.ordering.databinding.ItemFoodHorizontalBinding
import com.woowahan.ordering.databinding.ItemFoodVerticalBinding
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.ui.adapter.foodDiffUtil
import com.woowahan.ordering.ui.adapter.viewholder.ItemFoodViewHolder

class FoodAdapter(
    private var itemViewType: FoodItemViewType = FoodItemViewType.GridItem,
    private val onDetailClick: (String, String) -> Unit,
    private val onCartClick: (Food) -> Unit
) : ListAdapter<Food, ItemFoodViewHolder>(foodDiffUtil) {

    fun viewTypeChange(itemViewType: FoodItemViewType) {
        this.itemViewType = itemViewType
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            FoodItemViewType.GridItem.ordinal -> {
                val binding = ItemFoodGridBinding.inflate(inflater, parent, false)
                ItemFoodViewHolder.Grid(binding, onDetailClick, onCartClick)
            }
            FoodItemViewType.VerticalItem.ordinal -> {
                val binding = ItemFoodVerticalBinding.inflate(inflater, parent, false)
                ItemFoodViewHolder.Vertical(binding, onDetailClick, onCartClick)
            }
            FoodItemViewType.HorizontalItem.ordinal -> {
                val binding = ItemFoodHorizontalBinding.inflate(inflater, parent, false)
                ItemFoodViewHolder.Horizontal(binding, onDetailClick, onCartClick)
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    override fun onBindViewHolder(holder: ItemFoodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return itemViewType.ordinal
    }

    enum class FoodItemViewType {
        GridItem, VerticalItem, HorizontalItem
    }
}

