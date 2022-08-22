package com.woowahan.ordering.ui.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.ItemCountAndFilterBinding
import com.woowahan.ordering.domain.model.SortType

class CountAndFilterAdapter(
    private val onItemSelected: (SortType) -> Unit
) : RecyclerView.Adapter<CountAndFilterAdapter.CountAndFilterViewHolder>() {

    private var count = 0

    fun setCount(count: Int) {
        this.count = count
        notifyDataSetChanged()
    }

    inner class CountAndFilterViewHolder(
        private val binding: ItemCountAndFilterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val spinnerList = listOf(
                SortType.Default,
                SortType.MoneyDesc,
                SortType.MoneyAsc,
                SortType.RateDesc
            )

            binding.count = count

            val adapter = FilterAdapter(itemView.context, R.array.spinner)
            if (binding.spFilter.adapter == null) {
                binding.spFilter.adapter = adapter
                binding.spFilter.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            adapter.setCheckedItem(position)
                            onItemSelected(spinnerList[position])
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }
                    }
            }
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

}