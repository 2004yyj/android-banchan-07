package com.woowahan.ordering.ui.adapter.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.woowahan.ordering.databinding.ItemRecentlyGridBinding
import com.woowahan.ordering.databinding.ItemRecentlyHorizontalBinding
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.domain.model.Recently
import com.woowahan.ordering.ui.adapter.recentlyDiffUtil
import com.woowahan.ordering.ui.adapter.viewholder.ItemRecentlyViewHolder

class RecentlyAdapter(
    private var itemViewType: RecentlyItemViewType = RecentlyItemViewType.GridItem
) : ListAdapter<Recently, ItemRecentlyViewHolder>(recentlyDiffUtil) {

    private var onDetailClick: (String, String) -> Unit = { _, _ -> }
    private var onCartClick: (Food) -> Unit = {}

    fun setOnClick(onDetailClick: (String, String) -> Unit, onCartClick: (Food) -> Unit = {}) {
        this.onDetailClick = onDetailClick
        this.onCartClick = onCartClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRecentlyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            RecentlyItemViewType.GridItem.ordinal -> {
                val binding = ItemRecentlyGridBinding.inflate(inflater, parent, false)
                ItemRecentlyViewHolder.Grid(binding)
            }
            RecentlyItemViewType.HorizontalItem.ordinal -> {
                val binding = ItemRecentlyHorizontalBinding.inflate(inflater, parent, false)
                ItemRecentlyViewHolder.Horizontal(binding)
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    override fun onBindViewHolder(holder: ItemRecentlyViewHolder, position: Int) {
        when(holder) {
            is ItemRecentlyViewHolder.Grid -> holder.bind(getItem(position), onDetailClick, onCartClick)
            is ItemRecentlyViewHolder.Horizontal -> holder.bind(getItem(position), onDetailClick)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return itemViewType.ordinal
    }

    enum class RecentlyItemViewType {
        GridItem, HorizontalItem
    }
}