package com.woowahan.ordering.ui.fragment.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.woowahan.ordering.databinding.FragmentCartBinding
import com.woowahan.ordering.ui.adapter.cart.CartAdapter
import com.woowahan.ordering.ui.adapter.cart.CartRecentlyAdapter
import com.woowahan.ordering.ui.fragment.cart.recently.RecentlyViewedFragment
import com.woowahan.ordering.util.replace
import com.woowahan.ordering.ui.viewmodel.CartViewModel
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

    private fun initRecyclerView() = with(binding!!) {
        cartAdapter = CartAdapter(
            viewModel::selectAll,
            viewModel::checkItemClick,
            viewModel::minusItemClick,
            viewModel::plusItemClick,
            viewModel::deleteItemClick,
            viewModel::deleteAll,
            viewModel::orderClick
        )
        cartRecentlyAdapter = CartRecentlyAdapter {
            replaceToRecentlyViewed()
        }

        rvCart.adapter = ConcatAdapter(cartAdapter, cartRecentlyAdapter)
        rvCart.layoutManager = LinearLayoutManager(context)
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
                viewModel.message.collect {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun replaceToRecentlyViewed() {
        parentFragmentManager.replace(
            RecentlyViewedFragment::class.java,
            (requireView().parent as View).id,
            "RecentlyFragment"
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {

        fun newInstance() = CartFragment()
    }
}