package com.woowahan.ordering.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemRecentlyGridBinding
import com.woowahan.ordering.databinding.ItemRecentlyHorizontalBinding
import com.woowahan.ordering.domain.model.History

sealed class ItemHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    class Grid(private val binding: ItemRecentlyGridBinding) :
        ItemHistoryViewHolder(binding.root) {
        fun bind(
            history: History,
            onDetailClick: (String, String) -> Unit,
            onCartClick: (History) -> Unit
        ) {
            binding.recently = history
            binding.root.setOnClickListener {
                onDetailClick(history.title, history.detailHash)
            }
            binding.btnAddCart.setOnClickListener {
                onCartClick(history)
            }
        }
    }

    class Horizontal(private val binding: ItemRecentlyHorizontalBinding) :
        ItemHistoryViewHolder(binding.root) {
        fun bind(
            history: History,
            onDetailClick: (String, String) -> Unit
        ) {
            binding.recently = history
            binding.root.setOnClickListener {
                onDetailClick(history.title, history.detailHash)
            }
        }
    }
}