package com.woowahan.ordering.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemFoodBestBinding
import com.woowahan.ordering.domain.model.Best
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.ui.adapter.home.FoodAdapter
import com.woowahan.ordering.ui.adapter.home.FoodAdapter.FoodItemViewType
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
import com.woowahan.ordering.util.dp

class ItemFoodBestViewHolder(
    private val binding: ItemFoodBestBinding,
    onDetailClick: (String, String) -> Unit,
    onCartClick: (Food) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val adapter = FoodAdapter(FoodItemViewType.HorizontalItem, onDetailClick, onCartClick)
    private val decoration = ItemSpacingDecoratorWithHeader(
        spacing = 8.dp,
        spaceAdapters = listOf(adapter)
    )

    fun bind(best: Best) {
        adapter.submitList(best.items)

        with(binding) {
            title = best.name
            rvProducts.adapter = adapter
            if (rvProducts.itemDecorationCount == 0) {
                rvProducts.addItemDecoration(decoration)
            }
        }
    }

    fun submitList(best: Best) {
        adapter.submitList(best.items)
    }
}