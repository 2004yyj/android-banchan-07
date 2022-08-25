package com.woowahan.ordering.ui.adapter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemImageViewPagerBinding
import com.woowahan.ordering.ui.adapter.stringDiffUtil

class ImagesViewPagerAdapter :
    ListAdapter<String, ImagesViewPagerAdapter.ImagesViewPagerViewHolder>(stringDiffUtil) {

    inner class ImagesViewPagerViewHolder(
        private val binding: ItemImageViewPagerBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            binding.image = url
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewPagerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageViewPagerBinding.inflate(inflater, parent, false)
        return ImagesViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesViewPagerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}