package com.woowahan.ordering.ui.adapter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.woowahan.ordering.databinding.ItemDetailImagesBinding

class DetailThumbImagesAdapter() :
    RecyclerView.Adapter<DetailThumbImagesAdapter.DetailImagesViewHolder>() {
    inner class DetailImagesViewHolder(
        private val binding: ItemDetailImagesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val adapter = ImagesViewPagerAdapter()
            vpImages.adapter = adapter
            TabLayoutMediator(tbBottomDots, vpImages) { _, _ -> }.attach()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDetailImagesBinding.inflate(inflater)
        return DetailImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailImagesViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1

}