package com.woowahan.ordering.ui.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.woowahan.ordering.databinding.ItemFoodBestBinding
import com.woowahan.ordering.domain.model.Best
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.ui.adapter.bestDiffUtil
import com.woowahan.ordering.ui.adapter.viewholder.ItemFoodBestViewHolder

class BestFoodAdapter : ListAdapter<Best, ItemFoodBestViewHolder>(bestDiffUtil) {
    private var onDetailClick: (String, String) -> Unit = { _, _ -> }
    private var onCartClick: (Food) -> Unit = {}

    fun setOnClick(
        onDetailClick: (String, String) -> Unit,
        onCartClick: (Food) -> Unit
    ) {
        this.onDetailClick = onDetailClick
        this.onCartClick = onCartClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFoodBestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodBestBinding.inflate(inflater, parent, false)
        return ItemFoodBestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemFoodBestViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            onDetailClick = onDetailClick,
            onCartClick = onCartClick
        )
    }
}