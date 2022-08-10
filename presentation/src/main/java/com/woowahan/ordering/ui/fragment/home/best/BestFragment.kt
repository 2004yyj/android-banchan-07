package com.woowahan.ordering.ui.fragment.home.best

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.woowahan.ordering.databinding.FragmentBestBinding

class BestFragment(
    private val onDetailClick: (hash: String) -> Unit
) : Fragment() {

    private lateinit var binding: FragmentBestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBestBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        fun newInstance(onDetailClick: (hash: String) -> Unit) =
            BestFragment(onDetailClick)
    }
}