package com.woowahan.ordering.ui.adapter.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemCartRecentlyBinding
import com.woowahan.ordering.domain.model.History
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
import com.woowahan.ordering.util.dp

class CartHistoryAdapter(
    private val onDetailClick: (String, String) -> Unit,
    private val seeAllClick: () -> Unit
) : RecyclerView.Adapter<CartHistoryAdapter.CartHistoryViewHolder>() {

    private val historyList = mutableListOf<History>()

    fun submitList(list: List<History>) {
        historyList.clear()
        historyList.addAll(list)
        notifyDataSetChanged()
    }

    inner class CartHistoryViewHolder(private val binding: ItemCartRecentlyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val adapter = HistoryAdapter(HistoryAdapter.HistoryItemViewType.HorizontalItem)
        private val decoration = ItemSpacingDecoratorWithHeader(
            spacing = 8.dp,
            spaceAdapters = listOf(adapter)
        )

        fun bind(list: List<History>, seeAllClick: () -> Unit) = with(binding) {
            adapter.submitList(list)
            adapter.setOnClick(onDetailClick)

            tvSeeAll.setOnClickListener { seeAllClick() }
            rvRecently.adapter = adapter
            if (rvRecently.itemDecorationCount == 0) {
                rvRecently.addItemDecoration(decoration)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHistoryViewHolder {
        return CartHistoryViewHolder(
            ItemCartRecentlyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartHistoryViewHolder, position: Int) {
        holder.bind(historyList, seeAllClick)
    }

    override fun getItemCount(): Int = 1
}