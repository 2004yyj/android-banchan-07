package com.woowahan.ordering.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemFoodBestBinding
import com.woowahan.ordering.domain.model.Best
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.ui.adapter.home.FoodAdapter
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
import com.woowahan.ordering.util.dp

class ItemFoodBestViewHolder(
    private val binding: ItemFoodBestBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val decoration = ItemSpacingDecoratorWithHeader(8.dp)

    fun bind(
        best: Best,
        onDetailClick: (String, String) -> Unit,
        onCartClick: (Int, Food) -> Unit
    ) {
        val adapter = FoodAdapter()
        adapter.submitList(best.items)
        adapter.setOnClick(onDetailClick, onCartClick)

        with(binding) {
            title = best.name
            rvProducts.adapter = adapter
            if (rvProducts.itemDecorationCount == 0) {
                rvProducts.addItemDecoration(decoration)
            }
        }
    }
}