package com.woowahan.ordering.ui.fragment.home.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.woowahan.ordering.databinding.FragmentOtherDishBinding

class OtherDishFragment(
    private val onDetailClick: (hash: String) -> Unit
) : Fragment() {

    private lateinit var binding: FragmentOtherDishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtherDishBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        fun newInstance(onDetailClick: (hash: String) -> Unit) =
            OtherDishFragment(onDetailClick)
    }
}