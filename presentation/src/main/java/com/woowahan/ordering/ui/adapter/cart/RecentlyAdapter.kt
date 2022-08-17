package com.woowahan.ordering.ui.adapter.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.data.mapper.toFoodModel
import com.woowahan.ordering.databinding.ItemRecentlyViewedBinding
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.domain.model.Recently
import com.woowahan.ordering.ui.adapter.recentlyDiffUtil

class RecentlyAdapter(
    private val isCart: Boolean,
    private val cartClick: (Food) -> Unit = {}
) : ListAdapter<Recently, RecentlyAdapter.RecentlyViewHolder>(recentlyDiffUtil) {

    class RecentlyViewHolder(private val binding: ItemRecentlyViewedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recently: Recently, isCart: Boolean, cartClick: (Food) -> Unit) = with(binding) {
            this.recently = recently
            this.isCart = isCart

            if (!isCart) {
                btnAddCart.setOnClickListener { cartClick(recently.toFoodModel()) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyViewHolder {
        return RecentlyViewHolder(
            ItemRecentlyViewedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecentlyViewHolder, position: Int) {
        holder.bind(getItem(position), isCart, cartClick)
    }
}