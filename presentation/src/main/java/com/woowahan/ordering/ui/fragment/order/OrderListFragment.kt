package com.woowahan.ordering.ui.fragment.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.woowahan.ordering.databinding.FragmentOrderListBinding
import com.woowahan.ordering.ui.adapter.order.SimpleOrderListAdapter
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader.Companion.VERTICAL
import com.woowahan.ordering.ui.fragment.order.detail.OrderDetailFragment
import com.woowahan.ordering.ui.fragment.order.detail.OrderDetailFragment.Companion.DETAIL_TIME
import com.woowahan.ordering.ui.viewmodel.OrderListViewModel
import com.woowahan.ordering.util.dp
import com.woowahan.ordering.util.replace
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

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
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            adapter.loadStateFlow.collectLatest {
                showRecyclerView(
                    !(
                        it.source.refresh is LoadState.NotLoading &&
                        it.append.endOfPaginationReached &&
                        adapter.itemCount < 1
                    )
                )
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
            spaceAdapters = listOf(adapter),
            layoutDirection = VERTICAL
        ))
    }

    private fun showRecyclerView(recyclerViewVisible: Boolean) = with(binding!!) {
        layoutNoItem.root.isVisible = !recyclerViewVisible
        rvOrderList.isVisible = recyclerViewVisible
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