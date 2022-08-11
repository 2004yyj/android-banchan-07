package com.woowahan.ordering.ui.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.woowahan.ordering.databinding.ItemFoodBestBinding
import com.woowahan.ordering.domain.model.Best
import com.woowahan.ordering.ui.adapter.bestDiffUtil
import com.woowahan.ordering.ui.adapter.viewholder.ItemFoodBestViewHolder

class BestFoodAdapter: ListAdapter<Best, ItemFoodBestViewHolder>(bestDiffUtil) {
    private var onClick: (String, String) -> Unit = { _, _ -> }
    fun setOnClick(onClick: (String, String) -> Unit) {
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFoodBestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodBestBinding.inflate(inflater, parent, false)
        return ItemFoodBestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemFoodBestViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}