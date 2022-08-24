package com.woowahan.ordering.ui.adapter.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.woowahan.ordering.databinding.ItemRecentlyGridBinding
import com.woowahan.ordering.domain.model.History
import com.woowahan.ordering.ui.adapter.historyDiffUtil
import com.woowahan.ordering.ui.adapter.viewholder.ItemHistoryViewHolder

class HistoryPagingAdapter(
    private val onDetailClick: (String, String) -> Unit,
    private val onCartClick: (History) -> Unit
) : PagingDataAdapter<History, ItemHistoryViewHolder.Grid>(historyDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHistoryViewHolder.Grid {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecentlyGridBinding.inflate(inflater, parent, false)
        return ItemHistoryViewHolder.Grid(binding)
    }

    override fun onBindViewHolder(holder: ItemHistoryViewHolder.Grid, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onDetailClick, onCartClick)
        }
    }
}