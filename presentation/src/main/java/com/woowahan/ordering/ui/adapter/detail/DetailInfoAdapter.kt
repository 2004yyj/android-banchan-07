package com.woowahan.ordering.ui.adapter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemDetailInfoBinding
import com.woowahan.ordering.domain.model.FoodDetail

class DetailInfoAdapter: RecyclerView.Adapter<DetailInfoAdapter.DetailInfoViewHolder>() {
    private var title: String? = null
    private var foodDetail: FoodDetail? = null
    fun submitData(title: String, foodDetail: FoodDetail) {
        this.title = title
        this.foodDetail = foodDetail
        notifyDataSetChanged()
    }

    inner class DetailInfoViewHolder(
        private val binding: ItemDetailInfoBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            if (foodDetail != null) {
                binding.title = title
                binding.foodDetail = foodDetail
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDetailInfoBinding.inflate(inflater, parent, false)
        return DetailInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailInfoViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 1
}