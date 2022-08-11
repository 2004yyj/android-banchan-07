package com.woowahan.ordering.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.BottomSheetCartBinding
import com.woowahan.ordering.domain.model.Food

class CartBottomSheet(
    private val food: Food
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_cart, container, false)
        return binding.root
    }

}