package com.woowahan.ordering.ui.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemHeaderBinding

class HeaderAdapter(
    private val title: String,
    private val chip: String
): RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {
    inner class HeaderViewHolder(
        private val binding: ItemHeaderBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            tvChip.text = chip
            tvTitle.text = title
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHeaderBinding.inflate(inflater, parent, false)
        return HeaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind()
    }
    override fun getItemCount(): Int = 1
}