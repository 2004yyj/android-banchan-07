package com.woowahan.ordering.ui.adapter.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemOrderDetailListBinding
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.ui.adapter.cartDiffUtil

class OrderDetailListAdapter :
    ListAdapter<Cart, OrderDetailListAdapter.ViewHolder>(cartDiffUtil) {

    class ViewHolder(
        private val binding: ItemOrderDetailListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: Cart) = with(binding) {
            this.cart = cart
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOrderDetailListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}