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
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.FragmentMainDishBinding
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.ui.adapter.home.FoodAdapter
import com.woowahan.ordering.ui.adapter.home.FoodAdapter.FoodItemViewType
import com.woowahan.ordering.ui.adapter.home.HeaderAdapter
import com.woowahan.ordering.ui.adapter.home.TypeAndFilterAdapter
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader.Companion.GRID
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader.Companion.VERTICAL
import com.woowahan.ordering.ui.listener.setOnThrottleClickListener
import com.woowahan.ordering.ui.uistate.ListUiState
import com.woowahan.ordering.ui.viewmodel.MainDishViewModel
import com.woowahan.ordering.util.dp
import com.woowahan.ordering.util.removeAllItemDecorations
import com.woowahan.ordering.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainDishFragment : Fragment() {

    private var _binding: FragmentMainDishBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel by viewModels<MainDishViewModel>()

    private val headerAdapter by lazy {
        HeaderAdapter(getString(R.string.main_header_main_dish))
    }

    private val typeAndFilterAdapter by lazy {
        TypeAndFilterAdapter(
            onItemSelected = {
                viewModel.setSortType(it)
            },
            onListTypeChangeClicked = {
                viewModel.setViewType(it)
            }
        )
    }

    private val foodAdapter: FoodAdapter by lazy {
        FoodAdapter(
            onDetailClick = onDetailClick,
            onCartClick = openBottomSheet
        )
    }

    private val concatAdapter by lazy {
        ConcatAdapter(headerAdapter, typeAndFilterAdapter, foodAdapter)
    }

    private val gridDecoration by lazy {
        ItemSpacingDecoratorWithHeader(
            spacing = 18.dp,
            spaceAdapters = listOf(foodAdapter),
            GRID
        )
    }

    private val linearDecoration by lazy {
        ItemSpacingDecoratorWithHeader(
            spacing = 18.dp,
            spaceAdapters = listOf(foodAdapter),
            VERTICAL
        )
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

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.viewType.collectLatest {
                        typeAndFilterAdapter.setViewType(it)
                        if (!it) {
                            setGridLayoutManager()
                        } else {
                            setLinearLayoutManager()
                        }
                    }
                }

                launch {
                    viewModel.sortType.collectLatest {
                        typeAndFilterAdapter.setSortType(it)
                        initData()
                    }
                }
            }
        }
    }

    private fun initListener() {
        binding.layoutNoInternet.btnRetry.setOnThrottleClickListener {
            initData()
        }
        binding.srlMainDish.setOnRefreshListener {
            initData()
        }
    }

    private fun initRecyclerView() = with(binding) {
        rvMainDish.adapter = concatAdapter
    }

    private fun setGridLayoutManager() = with(binding) {
        foodAdapter.viewTypeChange(FoodItemViewType.GridItem)
        binding.rvMainDish.removeAllItemDecorations()
        binding.rvMainDish.removeItemDecoration(linearDecoration)
        binding.rvMainDish.addItemDecoration(gridDecoration)
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
        foodAdapter.viewTypeChange(FoodItemViewType.VerticalItem)
        binding.rvMainDish.removeAllItemDecorations()
        binding.rvMainDish.removeItemDecoration(gridDecoration)
        binding.rvMainDish.addItemDecoration(linearDecoration)
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