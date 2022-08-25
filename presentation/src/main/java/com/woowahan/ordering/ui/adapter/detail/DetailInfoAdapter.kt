package com.woowahan.ordering.ui.adapter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.doOnAttach
import androidx.core.view.doOnDetach
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.databinding.ItemDetailInfoBinding
import com.woowahan.ordering.domain.model.FoodDetail
import com.woowahan.ordering.ui.viewmodel.DetailViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class DetailInfoAdapter(private val viewModel: DetailViewModel, private val title: String) :
    RecyclerView.Adapter<DetailInfoAdapter.DetailInfoViewHolder>() {

    inner class DetailInfoViewHolder(
        private val binding: ItemDetailInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private var lifecycleOwner: LifecycleOwner? = null

        init {
            itemView.doOnAttach {
                lifecycleOwner = itemView.findViewTreeLifecycleOwner()
            }
            itemView.doOnDetach {
                lifecycleOwner = null
            }
        }

        fun bind() {
            binding.vm = viewModel
            binding.title = title
            binding.lifecycleOwner = lifecycleOwner
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