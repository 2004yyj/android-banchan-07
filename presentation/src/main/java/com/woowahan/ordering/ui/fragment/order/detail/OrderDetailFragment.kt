package com.woowahan.ordering.ui.fragment.order.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.FragmentOrderDetailBinding
import com.woowahan.ordering.ui.adapter.order.OrderDetailFooterAdapter
import com.woowahan.ordering.ui.adapter.order.OrderDetailHeaderAdapter
import com.woowahan.ordering.ui.adapter.order.OrderDetailListAdapter
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader.Companion.VERTICAL
import com.woowahan.ordering.ui.viewmodel.OrderDetailViewModel
import com.woowahan.ordering.util.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailFragment : Fragment() {

    private val viewModel: OrderDetailViewModel by viewModels()
    private var binding: FragmentOrderDetailBinding? = null
    private var deliveryTime: Long = 0L

    private lateinit var header: OrderDetailHeaderAdapter
    private lateinit var ordersAdapter: OrderDetailListAdapter
    private lateinit var footer: OrderDetailFooterAdapter

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
        initToolbar()
    }

    private fun initToolbar() = with(binding!!) {
        toolbarOrder.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        toolbarOrder.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_refresh -> {
                    viewModel.getOrderedCart(deliveryTime)
                }
            }
            true
        }
    }

    private fun initFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.orderedCartList.collect {
                header.submitData(deliveryTime, it.count)
                ordersAdapter.submitList(it.list)
                footer.submitData(it.sumOfPrice, it.isNeedDeliveryFee)
            }
        }
    }

    private fun initArguments() {
        deliveryTime = requireArguments().getLong(DETAIL_TIME, 0L)
    }

    private fun initRecyclerView() = with(binding!!) {
        header = OrderDetailHeaderAdapter()
        ordersAdapter = OrderDetailListAdapter()
        footer = OrderDetailFooterAdapter()

        rvOrderDetail.adapter = ConcatAdapter(header, ordersAdapter, footer)
        rvOrderDetail.addItemDecoration(
            ItemSpacingDecoratorWithHeader(
                spacing = 16.dp,
                spaceAdapters = listOf(ordersAdapter),
                layoutDirection = VERTICAL
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val TAG = "OrderDetail"
        const val DETAIL_TIME = "detailTime"
    }
}