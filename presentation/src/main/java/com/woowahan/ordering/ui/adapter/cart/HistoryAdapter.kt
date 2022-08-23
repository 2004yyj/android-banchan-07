package com.woowahan.ordering.ui.adapter.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.woowahan.ordering.databinding.ItemRecentlyGridBinding
import com.woowahan.ordering.databinding.ItemRecentlyHorizontalBinding
import com.woowahan.ordering.domain.model.History
import com.woowahan.ordering.ui.adapter.historyDiffUtil
import com.woowahan.ordering.ui.adapter.viewholder.ItemHistoryViewHolder

class HistoryAdapter(
    private val itemViewType: HistoryItemViewType = HistoryItemViewType.GridItem
) : ListAdapter<History, ItemHistoryViewHolder>(historyDiffUtil) {

    private var onDetailClick: (String, String) -> Unit = { _, _ -> }
    private var onCartClick: (History) -> Unit = {}

    fun setOnClick(onDetailClick: (String, String) -> Unit, onCartClick: (History) -> Unit = {}) {
        this.onDetailClick = onDetailClick
        this.onCartClick = onCartClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            HistoryItemViewType.GridItem.ordinal -> {
                val binding = ItemRecentlyGridBinding.inflate(inflater, parent, false)
                ItemHistoryViewHolder.Grid(binding)
            }
            HistoryItemViewType.HorizontalItem.ordinal -> {
                val binding = ItemRecentlyHorizontalBinding.inflate(inflater, parent, false)
                ItemHistoryViewHolder.Horizontal(binding)
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    override fun onBindViewHolder(holder: ItemHistoryViewHolder, position: Int) {
        when(holder) {
            is ItemHistoryViewHolder.Grid -> holder.bind(getItem(position), onDetailClick, onCartClick)
            is ItemHistoryViewHolder.Horizontal -> holder.bind(getItem(position), onDetailClick)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return itemViewType.ordinal
    }

    enum class HistoryItemViewType {
        GridItem, HorizontalItem
    }
}