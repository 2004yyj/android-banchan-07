package com.woowahan.ordering.ui.fragment.home.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.woowahan.ordering.databinding.FragmentMainDishBinding

class MainDishFragment(
    private val onDetailClick: (title: String, hash: String) -> Unit
) : Fragment() {

    private lateinit var binding: FragmentMainDishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainDishBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        fun newInstance(onDetailClick: (title: String, hash: String) -> Unit) =
            MainDishFragment(onDetailClick)
    }
}