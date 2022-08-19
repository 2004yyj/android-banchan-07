package com.woowahan.ordering.ui.fragment.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.woowahan.ordering.contracts.DELIVERY_TIME
import com.woowahan.ordering.databinding.FragmentCartBinding
import com.woowahan.ordering.ui.adapter.cart.CartAdapter
import com.woowahan.ordering.ui.adapter.cart.CartRecentlyAdapter
import com.woowahan.ordering.ui.fragment.cart.recently.RecentlyViewedFragment
import com.woowahan.ordering.ui.fragment.detail.DetailFragment
import com.woowahan.ordering.ui.fragment.detail.DetailFragment.Companion.HASH
import com.woowahan.ordering.ui.fragment.detail.DetailFragment.Companion.TITLE
import com.woowahan.ordering.ui.fragment.order.detail.OrderDetailFragment
import com.woowahan.ordering.ui.receiver.CartReceiver
import com.woowahan.ordering.ui.receiver.CartReceiver.Companion.DELIVERY_FINISHED_TIME
import com.woowahan.ordering.ui.receiver.CartReceiver.Companion.FOOD_COUNT
import com.woowahan.ordering.ui.receiver.CartReceiver.Companion.FOOD_TITLE
import com.woowahan.ordering.ui.uistate.CartUiState
import com.woowahan.ordering.util.replace
import com.woowahan.ordering.ui.viewmodel.CartViewModel
import com.woowahan.ordering.util.clearAllBackStack
import com.woowahan.ordering.util.replaceWithPopBackstack
import com.woowahan.ordering.util.startAlarmReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment() {
    private var binding: FragmentCartBinding? = null
    private val viewModel by viewModels<CartViewModel>()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartRecentlyAdapter: CartRecentlyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initFlow()
    }

    private fun initRecyclerView() {
        cartAdapter = CartAdapter(
            viewModel::selectAll,
            viewModel::checkItemClick,
            viewModel::minusItemClick,
            viewModel::plusItemClick,
            viewModel::deleteItemClick,
            viewModel::deleteAll,
            orderClick = { title, count ->
                val deliveryTime = System.currentTimeMillis() + DELIVERY_TIME
                viewModel.orderClick(deliveryTime, title, count)
            }
        )

        cartRecentlyAdapter = CartRecentlyAdapter(
            onDetailClick = this::replaceToDetail,
            seeAllClick = this::replaceToRecentlyViewed
        )

        with(binding!!) {
            rvCart.adapter = ConcatAdapter(cartAdapter, cartRecentlyAdapter)
            rvCart.layoutManager = LinearLayoutManager(context)
            rvCart.itemAnimator = null
        }
    }

    private fun createNotificationTray(title: String, count: Int, deliveryTime: Long) {
        with(requireContext()) {
            val intent = Intent(this, CartReceiver::class.java).apply {
                putExtra(DELIVERY_FINISHED_TIME, deliveryTime)
                putExtra(FOOD_TITLE, title)
                putExtra(FOOD_COUNT, count)
            }
            startAlarmReceiver(intent, deliveryTime)
        }
    }

    private fun initFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cartList.collect {
                    cartAdapter.submitList(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recentlyList.collect {
                    cartRecentlyAdapter.submitList(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is CartUiState.Loading -> {}
                        is CartUiState.SuccessOrder -> {
                            replaceToOrderDetail(it.deliveryTime)
                            createNotificationTray(it.title, it.count, it.deliveryTime)
                        }
                        is CartUiState.Error -> {
                            Toast.makeText(requireContext(), "오류가 발생했습니다.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

            }
        }
    }

    private fun replaceToOrderDetail(deliveryTime: Long) {
        parentFragmentManager.replaceWithPopBackstack(
            OrderDetailFragment::class.java,
            (requireView().parent as View).id,
            OrderDetailFragment.TAG,
            bundleOf(OrderDetailFragment.DETAIL_TIME to deliveryTime)
        )
    }

    private fun replaceToRecentlyViewed() {
        parentFragmentManager.replace(
            RecentlyViewedFragment::class.java,
            (requireView().parent as View).id,
            RecentlyViewedFragment.TAG
        )
    }

    private fun replaceToDetail(title: String, hash: String) {
        parentFragmentManager.clearAllBackStack(tag)
        parentFragmentManager.replace(
            DetailFragment::class.java,
            (requireView().parent as View).id,
            DetailFragment.TAG,
            bundleOf(TITLE to title, HASH to hash)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val TAG = "Cart"
        fun newInstance() = CartFragment()
    }
}