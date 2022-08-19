package com.woowahan.ordering.ui.adapter.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.constants.DEFAULT_DELIVERY_FEE
import com.woowahan.ordering.databinding.ItemOrderDetailFooterBinding

class OrderDetailFooterAdapter : RecyclerView.Adapter<OrderDetailFooterAdapter.ViewHolder>() {

    private var sum = 0L
    private var isNeedDeliveryFee = false

    fun submitData(sum: Long, isNeededDeliveryFee: Boolean) {
        this.sum = sum
        this.isNeedDeliveryFee = isNeededDeliveryFee
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemOrderDetailFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val deliveryFee = if (isNeedDeliveryFee) DEFAULT_DELIVERY_FEE else 0

            this.sumOfPrice = sum
            this.deliveryFee = deliveryFee
            this.totalPrice = sum + deliveryFee
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOrderDetailFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1
}