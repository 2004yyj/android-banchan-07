package com.woowahan.ordering.ui.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemCountAndFilterBinding

class CountAndFilterAdapter(
) : RecyclerView.Adapter<CountAndFilterAdapter.CountAndFilterViewHolder>() {

    private var count = 0

    inner class CountAndFilterViewHolder(
        private val binding: ItemCountAndFilterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.count = count
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountAndFilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCountAndFilterBinding.inflate(inflater, parent, false)
        return CountAndFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountAndFilterViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1

    fun setCount(count: Int) {
        this.count = count
        notifyDataSetChanged()
    }
}