package com.woowahan.ordering.ui.fragment.home.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.FragmentMainDishBinding
import com.woowahan.ordering.domain.model.Best
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.domain.model.Menu
import com.woowahan.ordering.ui.adapter.home.FoodAdapter
import com.woowahan.ordering.ui.adapter.home.FoodAdapter.FoodItemViewType
import com.woowahan.ordering.ui.adapter.home.HeaderAdapter
import com.woowahan.ordering.ui.adapter.home.TypeAndFilterAdapter
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader.Companion.GRID
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader.Companion.VERTICAL
import com.woowahan.ordering.ui.fragment.home.HomeFragment.Companion.TAG
import com.woowahan.ordering.ui.uistate.ListUiState
import com.woowahan.ordering.ui.viewmodel.MainDishViewModel
import com.woowahan.ordering.util.dp
import com.woowahan.ordering.util.hasNetwork
import com.woowahan.ordering.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class MainDishFragment : Fragment() {

    private var _binding: FragmentMainDishBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel by viewModels<MainDishViewModel>()

    private val typeAndFilterAdapter by lazy { TypeAndFilterAdapter() }
    private lateinit var foodAdapter: FoodAdapter

    private var onDetailClick: (title: String, hash: String) -> Unit = { _, _ -> }
    fun setOnDetailClick(onDetailClick: (title: String, hash: String) -> Unit) {
        this.onDetailClick = onDetailClick
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainDishBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initRecyclerView()
        initFlow()
        initListener()
    }

    private fun initData() {
        if (requireContext().hasNetwork()) {
            viewModel.getMenuList(Menu.Main)
            showRecyclerView()
        } else {
            requireContext().showToast(getString(R.string.no_internet_message))
            hideRecyclerView()
        }
    }

    private fun showRecyclerView() = with(binding) {
        layoutNoInternet.root.isVisible = false
        srlMainDish.isVisible = true
    }

    private fun hideRecyclerView() = with(binding) {
        layoutNoInternet.root.isVisible = true
        srlMainDish.isVisible = false
    }

    private fun initFlow() {
        lifecycleScope.launch {
            viewModel.menu.flowWithLifecycle(
                lifecycle = lifecycle,
                minActiveState = Lifecycle.State.STARTED
            ).collect {
                when (it) {
                    is ListUiState.Refreshing -> {}
                    is ListUiState.List<Food> -> {
                        binding.srlMainDish.isRefreshing = false
                        foodAdapter.submitList(it.list)
                    }
                }
            }
        }
    }

    private fun initListener() {
        foodAdapter.setOnClick(
            onDetailClick = onDetailClick,
            onCartClick = openBottomSheet
        )
        binding.layoutNoInternet.btnRetry.setOnClickListener {
            initData()
        }
        binding.srlMainDish.setOnRefreshListener {
            initData()
        }
    }

    private fun initRecyclerView() = with(binding) {
        foodAdapter = FoodAdapter()
        val headerAdapter = HeaderAdapter(getString(R.string.main_header_main_dish))
        val concatAdapter = ConcatAdapter(headerAdapter, typeAndFilterAdapter, foodAdapter)
        val gridDecoration = ItemSpacingDecoratorWithHeader(
            spacing = 18.dp,
            spaceAdapters = listOf(foodAdapter),
            GRID
        )

        val linearDecoration = ItemSpacingDecoratorWithHeader(
            spacing = 18.dp,
            spaceAdapters = listOf(foodAdapter),
            VERTICAL
        )

        typeAndFilterAdapter.setOnItemSelected {
            viewModel.getMenuList(Menu.Main, it)
        }

        typeAndFilterAdapter.setOnListTypeChangeClicked {
            if (!it) {
                foodAdapter.viewTypeChange(FoodItemViewType.GridItem)
                rvMainDish.removeItemDecoration(linearDecoration)
                rvMainDish.addItemDecoration(gridDecoration)
                setGridLayoutManager(concatAdapter)
            } else {
                foodAdapter.viewTypeChange(FoodItemViewType.VerticalItem)
                rvMainDish.removeItemDecoration(gridDecoration)
                rvMainDish.addItemDecoration(linearDecoration)
                setLinearLayoutManager()
            }
        }
        rvMainDish.adapter = concatAdapter
        rvMainDish.addItemDecoration(gridDecoration)
        setGridLayoutManager(concatAdapter)
    }

    private fun setGridLayoutManager(concatAdapter: ConcatAdapter) = with(binding) {
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val adapter = concatAdapter.getWrappedAdapterAndPosition(position).first
                return if (adapter is FoodAdapter) 1 else 2
            }
        }
        rvMainDish.layoutManager = layoutManager
    }

    private fun setLinearLayoutManager() = with(binding) {
        rvMainDish.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private lateinit var openBottomSheet: (Food) -> Unit

        fun newInstance(openBottomSheet: (Food) -> Unit): MainDishFragment {
            this.openBottomSheet = openBottomSheet
            return MainDishFragment()
        }
    }
}