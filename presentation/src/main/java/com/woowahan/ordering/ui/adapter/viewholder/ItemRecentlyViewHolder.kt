package com.woowahan.ordering.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemRecentlyGridBinding
import com.woowahan.ordering.databinding.ItemRecentlyHorizontalBinding
import com.woowahan.ordering.domain.model.Recently

sealed class ItemRecentlyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    class Grid(private val binding: ItemRecentlyGridBinding) :
        ItemRecentlyViewHolder(binding.root) {
        fun bind(
            recently: Recently,
            onDetailClick: (String, String) -> Unit,
            onCartClick: (Recently) -> Unit
        ) {
            binding.recently = recently
            binding.root.setOnClickListener {
                onDetailClick(recently.title, recently.detailHash)
            }
            binding.btnAddCart.setOnClickListener {
                onCartClick(recently)
            }
        }
    }

    class Horizontal(private val binding: ItemRecentlyHorizontalBinding) :
        ItemRecentlyViewHolder(binding.root) {
        fun bind(
            recently: Recently,
            onDetailClick: (String, String) -> Unit
        ) {
            binding.recently = recently
            binding.root.setOnClickListener {
                onDetailClick(recently.title, recently.detailHash)
            }
        }
    }
}