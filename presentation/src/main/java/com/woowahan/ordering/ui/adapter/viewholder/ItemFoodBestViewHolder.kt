package com.woowahan.ordering.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemFoodBestBinding
import com.woowahan.ordering.domain.model.Best
import com.woowahan.ordering.ui.adapter.home.FoodGridAdapter
import com.woowahan.ordering.ui.decorator.ItemSpacingDecorator
import com.woowahan.ordering.util.toDp

class ItemFoodBestViewHolder(
    private val binding: ItemFoodBestBinding
): RecyclerView.ViewHolder(binding.root) {

    private val decoration = ItemSpacingDecorator(1.toDp(itemView.resources))

    fun bind(best: Best, onClick: (String) -> Unit) {
        val adapter = FoodGridAdapter()
        adapter.submitList(best.items)
        adapter.setOnClick {
            onClick(it)
        }

        with(binding) {
            title = best.name
            rvProducts.adapter = adapter

            if (rvProducts.itemDecorationCount == 0)
                rvProducts.addItemDecoration(decoration)
        }
    }
}