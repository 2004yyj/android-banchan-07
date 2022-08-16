package com.woowahan.ordering.ui.adapter.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemCartRecentlyBinding
import com.woowahan.ordering.domain.model.Recently
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
import com.woowahan.ordering.util.dp

class CartRecentlyAdapter : RecyclerView.Adapter<CartRecentlyAdapter.CartRecentlyItemViewHolder>() {

    private val recentlyList = mutableListOf<Recently>()

    fun submitList(list: List<Recently>) {
        recentlyList.clear()
        recentlyList.addAll(list)
    }

    class CartRecentlyItemViewHolder(private val binding: ItemCartRecentlyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val decoration = ItemSpacingDecoratorWithHeader(8.dp)

        fun bind(list: List<Recently>) = with(binding) {
            val adapter = RecentlyAdapter(true)
            adapter.submitList(list)

            rvRecently.adapter = adapter
            if (rvRecently.itemDecorationCount == 0) {
                rvRecently.addItemDecoration(decoration)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartRecentlyItemViewHolder {
        return CartRecentlyItemViewHolder(
            ItemCartRecentlyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartRecentlyItemViewHolder, position: Int) {
        holder.bind(recentlyList)
    }

    override fun getItemCount(): Int = 1
}