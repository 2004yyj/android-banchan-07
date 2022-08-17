package com.woowahan.ordering.ui.fragment.order.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.woowahan.ordering.databinding.FragmentOrderDetailBinding
import com.woowahan.ordering.ui.adapter.order.OrderDetailHeaderAdapter
import com.woowahan.ordering.ui.viewmodel.OrderDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailFragment : Fragment() {

    private val viewModel: OrderDetailViewModel by viewModels()
    private var binding: FragmentOrderDetailBinding? = null
    private var deliveryTime: Long = 0L

    private lateinit var header: OrderDetailHeaderAdapter

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
                header.submitData(deliveryTime, it.count)
            }
        }
    }

    private fun initArguments() {
        deliveryTime = requireArguments().getLong(DETAIL_TIME, 0L)
    }

    private fun initRecyclerView() = with(binding!!) {
        header = OrderDetailHeaderAdapter()
        rvOrderDetail.adapter = ConcatAdapter(header)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val DETAIL_TIME = "detailTime"
    }
}