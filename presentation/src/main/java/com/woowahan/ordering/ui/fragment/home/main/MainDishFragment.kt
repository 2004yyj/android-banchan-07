package com.woowahan.ordering.ui.fragment.home.main

import android.os.Bundle
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
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.domain.model.Menu
import com.woowahan.ordering.ui.adapter.home.FoodAdapter
import com.woowahan.ordering.ui.adapter.home.FoodAdapter.FoodItemViewType
import com.woowahan.ordering.ui.adapter.home.HeaderAdapter
import com.woowahan.ordering.ui.adapter.home.TypeAndFilterAdapter
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader.Companion.GRID
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader.Companion.VERTICAL
import com.woowahan.ordering.ui.uistate.ListUiState
import com.woowahan.ordering.ui.viewmodel.MainDishViewModel
import com.woowahan.ordering.util.dp
import com.woowahan.ordering.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainDishFragment : Fragment() {

    private var _binding: FragmentMainDishBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel by viewModels<MainDishViewModel>()

    private val typeAndFilterAdapter by lazy {
        TypeAndFilterAdapter {
            viewModel.sortType = it
            initData()
        }
    }
    private lateinit var foodAdapter: FoodAdapter

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
        viewModel.getMenuList()
    }

    private fun showRecyclerView() = with(binding) {
        layoutNoInternet.root.isVisible = false
        binding.srlMainDish.isRefreshing = false
        srlMainDish.isVisible = true
    }

    private fun showNoInternetConnection() = with(binding) {
        requireContext().showToast(getString(R.string.no_internet_message))
        layoutNoInternet.root.isVisible = true
        binding.srlMainDish.isRefreshing = false
        srlMainDish.isVisible = false
    }

    private fun initFlow() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(
                lifecycle = lifecycle,
                minActiveState = Lifecycle.State.STARTED
            ).collectLatest {
                when (it) {
                    is ListUiState.Refreshing -> {}
                    is ListUiState.List<Food> -> {
                        showRecyclerView()
                        foodAdapter.submitList(it.list)
                    }
                    is ListUiState.NoInternet -> {
                        showNoInternetConnection()
                    }
                }
            }
        }
    }

    private fun initListener() {
        binding.layoutNoInternet.btnRetry.setOnClickListener {
            initData()
        }
        binding.srlMainDish.setOnRefreshListener {
            initData()
        }
    }

    private fun initRecyclerView() = with(binding) {
        foodAdapter = FoodAdapter(onDetailClick = onDetailClick, onCartClick = openBottomSheet)
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
        private lateinit var onDetailClick: (title: String, hash: String) -> Unit
        private lateinit var openBottomSheet: (Food) -> Unit

        fun newInstance(
            onDetailClick: (title: String, hash: String) -> Unit,
            openBottomSheet: (Food) -> Unit
        ): MainDishFragment {
            this.onDetailClick = onDetailClick
            this.openBottomSheet = openBottomSheet
            return MainDishFragment()
        }
    }
}