package com.woowahan.ordering.ui.fragment.order.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.FragmentOrderDetailBinding
import com.woowahan.ordering.ui.viewmodel.OrderDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class OrderDetailFragment : Fragment() {

    private val viewModel: OrderDetailViewModel by viewModels()
    private var binding: FragmentOrderDetailBinding? = null
    private var deliveryTime: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderDetailBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFlow()
        initArguments()
        initRecyclerView()
        viewModel.getOrderedCart(deliveryTime)
    }

    private fun initFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.orderedCartList.collect {
                
            }
        }
    }

    private fun initArguments() {
        deliveryTime = requireArguments().getLong("deliveryTime", 0L)
    }

    private fun initRecyclerView() = with(binding!!) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}