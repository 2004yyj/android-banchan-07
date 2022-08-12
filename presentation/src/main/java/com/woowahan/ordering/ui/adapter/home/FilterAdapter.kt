package com.woowahan.ordering.ui.adapter.home

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.ArrayRes
import com.woowahan.ordering.databinding.SpinnerFilterDropdownBinding
import com.woowahan.ordering.databinding.SpinnerFilterTopBinding

class FilterAdapter(
    context: Context,
    @ArrayRes filterListResources: Int
) : ArrayAdapter<String>(context, 0, context.resources.getStringArray(filterListResources)) {

    private lateinit var dropdownBinding: SpinnerFilterDropdownBinding
    private lateinit var topBinding: SpinnerFilterTopBinding

    private var checkedItem = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (convertView == null) {
            val inflater = LayoutInflater.from(parent.context)
            topBinding = SpinnerFilterTopBinding.inflate(inflater, parent, false)
        }
        val view: View = convertView ?: topBinding.root
        bindTopView(position)

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (convertView == null) {
            val inflater = LayoutInflater.from(parent.context)
            dropdownBinding = SpinnerFilterDropdownBinding.inflate(inflater, parent, false)
        }
        val view: View = convertView ?: dropdownBinding.root
        bindDropDownView(position)

        return view
    }

    private fun bindTopView(position: Int) = with(topBinding) {
        tvTitle.text = getItem(position)
    }

    private fun bindDropDownView(position: Int) = with(dropdownBinding) {
        tvOptionTitle.text = getItem(position)
        if (position == checkedItem) {
            tvOptionTitle.typeface = Typeface.DEFAULT_BOLD
            ivCheck.visibility = View.VISIBLE
        } else {
            tvOptionTitle.typeface = Typeface.DEFAULT
            ivCheck.visibility = View.GONE
        }
    }

    fun setCheckedItem(position: Int) {
        checkedItem = position
    }
}