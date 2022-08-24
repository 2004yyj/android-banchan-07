package com.woowahan.ordering.ui.adapter.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemOrderSimpleBinding
import com.woowahan.ordering.domain.model.SimpleOrder
import com.woowahan.ordering.ui.adapter.simpleOrderDiffUtil

class SimpleOrderListAdapter(
    private val onClickOrderItem: (deliveryTime: Long) -> Unit
) : PagingDataAdapter<SimpleOrder, SimpleOrderListAdapter.ViewHolder>(
    simpleOrderDiffUtil
) {
    inner class ViewHolder(private val binding: ItemOrderSimpleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(simpleOrder: SimpleOrder, onClickOrderItem: (deliveryTime: Long) -> Unit) {
            binding.simpleOrder = simpleOrder
            binding.root.setOnClickListener {
                onClickOrderItem(simpleOrder.deliveryTime)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderSimpleBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onClickOrderItem) }
    }
}