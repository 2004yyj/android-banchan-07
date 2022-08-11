package com.woowahan.ordering.ui.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.ItemTypeAndFilterBinding
import com.woowahan.ordering.domain.model.SortType

class TypeAndFilterAdapter :
    RecyclerView.Adapter<TypeAndFilterAdapter.TypeAndFilterViewHolder>() {

    private var onItemSelected: (SortType) -> Unit = {}
    private var onListTypeChangeClicked: (Boolean) -> Unit = {}

    fun setOnItemSelected(onItemSelected: (SortType) -> Unit) {
        this.onItemSelected = onItemSelected
    }

    fun setOnListTypeChangeClicked(onListTypeChangeClicked: (Boolean) -> Unit) {
        this.onListTypeChangeClicked = onListTypeChangeClicked
    }

    inner class TypeAndFilterViewHolder(
        private val binding: ItemTypeAndFilterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val spinnerList = listOf(
                SortType.Default,
                SortType.MoneyDesc,
                SortType.MoneyAsc,
                SortType.RateDesc
            )

            val adapter = FilterAdapter(itemView.context, R.array.spinner)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeAndFilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTypeAndFilterBinding.inflate(inflater, parent, false)
        return TypeAndFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TypeAndFilterViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1
}