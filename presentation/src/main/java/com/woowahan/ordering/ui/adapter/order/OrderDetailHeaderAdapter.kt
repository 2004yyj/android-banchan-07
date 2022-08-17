package com.woowahan.ordering.ui.adapter.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemOrderDetailHeaderBinding

class OrderDetailHeaderAdapter: RecyclerView.Adapter<OrderDetailHeaderAdapter.OrderDetailHeaderViewHolder>() {

    private var deliveryTime: Long = 0L
    private var count: Int = 0

    fun submitData(deliveryTime: Long, count: Int) {
        this.deliveryTime = deliveryTime
        this.count = count
        notifyDataSetChanged()
    }

    inner class OrderDetailHeaderViewHolder(
        private val binding: ItemOrderDetailHeaderBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            this.deliveryTime = deliveryTime
            this.count = count
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailHeaderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderDetailHeaderBinding.inflate(inflater, parent, false)
        return OrderDetailHeaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderDetailHeaderViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1
}