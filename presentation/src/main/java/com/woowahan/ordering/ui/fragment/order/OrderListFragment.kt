package com.woowahan.ordering.ui.fragment.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.FragmentOrderListBinding
import com.woowahan.ordering.ui.adapter.order.SimpleOrderListAdapter
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader.Companion.VERTICAL
import com.woowahan.ordering.ui.fragment.detail.DetailFragment
import com.woowahan.ordering.ui.fragment.home.HomeFragment
import com.woowahan.ordering.ui.fragment.order.detail.OrderDetailFragment
import com.woowahan.ordering.ui.fragment.order.detail.OrderDetailFragment.Companion.DETAIL_TIME
import com.woowahan.ordering.ui.viewmodel.OrderListViewModel
import com.woowahan.ordering.util.dp
import com.woowahan.ordering.util.replace
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderListFragment : Fragment() {

    private val viewModel: OrderListViewModel by viewModels()
    private lateinit var adapter: SimpleOrderListAdapter
    private var binding: FragmentOrderListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderListBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFlow()
        initRecyclerView()

        viewModel.getSimpleOrder()
    }

    private fun initFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.simpleOrder.collect {
                adapter.submitList(it)
            }
        }
    }

    private fun initRecyclerView() = with(binding!!) {
        adapter = SimpleOrderListAdapter {
            replaceToOrderDetail(it)
        }
        rvOrderList.adapter = adapter
        rvOrderList.addItemDecoration(ItemSpacingDecoratorWithHeader(
            spacing = 9.dp,
            removeSpacePosition = listOf(),
            layoutDirection = VERTICAL
        ))
    }

    private fun replaceToOrderDetail(deliveryTime: Long) {
        parentFragmentManager.replace(
            OrderDetailFragment::class.java,
            (requireView().parent as View).id,
            OrderDetailFragment.TAG,
            bundleOf(DETAIL_TIME to deliveryTime)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val TAG = "OrderList"
    }
}