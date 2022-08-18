package com.woowahan.ordering.ui.fragment.cart.recently

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.woowahan.ordering.databinding.FragmentRecentlyViewedBinding
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.ui.adapter.cart.RecentlyAdapter
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader.Companion.GRID
import com.woowahan.ordering.ui.dialog.CartBottomSheet
import com.woowahan.ordering.ui.dialog.CartDialogFragment
import com.woowahan.ordering.ui.dialog.IsExistsCartDialogFragment
import com.woowahan.ordering.ui.viewmodel.RecentlyViewedViewModel
import com.woowahan.ordering.util.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentlyViewedFragment : Fragment() {

    private var binding: FragmentRecentlyViewedBinding? = null
    private val viewModel by viewModels<RecentlyViewedViewModel>()
    private lateinit var adapter: RecentlyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecentlyViewedBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initFlow()
    }

    private fun initRecyclerView() = with(binding!!) {
        adapter = RecentlyAdapter()
        rvRecently.adapter = adapter
        rvRecently.addItemDecoration(
            ItemSpacingDecoratorWithHeader(
                spacing = 16.dp,
                layoutDirection = GRID
            )
        )
    }

    private fun initFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.recentlyViewedList.collect {
                adapter.submitList(it)
            }
        }
    }

    private fun showCartBottomSheet(food: Food) {
        if (food.isAdded) {
            IsExistsCartDialogFragment.newInstance {
                navigateToCart()
            }.show(parentFragmentManager, tag)
        } else {
            CartBottomSheet.newInstance(food) {
                showCartDialog()
            }.show(parentFragmentManager, tag)
        }
    }

    private fun showCartDialog() {
        CartDialogFragment.newInstance {
            navigateToCart()
        }.show(parentFragmentManager, tag)
    }

    private fun navigateToCart() {
        // TODO
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}