package com.woowahan.ordering.ui.adapter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemImageFooterBinding
import com.woowahan.ordering.databinding.ItemImageViewPagerBinding
import com.woowahan.ordering.ui.adapter.stringDiffUtil

class DetailImagesFooterAdapter :
    ListAdapter<String, DetailImagesFooterAdapter.DetailImagesFooterViewHolder>(stringDiffUtil) {

    inner class DetailImagesFooterViewHolder(
        private val binding: ItemImageFooterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            binding.image = url
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailImagesFooterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageFooterBinding.inflate(inflater, parent, false)
        return DetailImagesFooterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailImagesFooterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}