package com.woowahan.ordering.ui.fragment.home.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.FragmentOtherDishBinding
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.ui.adapter.home.CountAndFilterAdapter
import com.woowahan.ordering.ui.adapter.home.FoodAdapter
import com.woowahan.ordering.ui.adapter.home.HeaderAdapter
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader.Companion.GRID
import com.woowahan.ordering.ui.fragment.home.other.kind.OtherKind
import com.woowahan.ordering.ui.listener.setOnThrottleClickListener
import com.woowahan.ordering.ui.uistate.ListUiState
import com.woowahan.ordering.ui.viewmodel.OtherDishViewModel
import com.woowahan.ordering.util.dp
import com.woowahan.ordering.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OtherDishFragment : Fragment() {

    private var _binding: FragmentOtherDishBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel by viewModels<OtherDishViewModel>()
    private val kind by lazy { requireArguments().get(OTHER_KIND) as OtherKind }

    private val headerAdapter by lazy {
        HeaderAdapter(
            title = when (kind) {
                OtherKind.Soup -> getString(R.string.main_header_soup)
                OtherKind.Side -> getString(R.string.main_header_side)
            }
        )
    }

    private val countAndFilterAdapter by lazy {
        CountAndFilterAdapter(
            onItemSelected = {
                viewModel.setSortType(it)
            }
        )
    }

    private val foodAdapter by lazy {
        FoodAdapter(
            onDetailClick = onDetailClick,
            onCartClick = openBottomSheet
        )
    }

    private val concatAdapter by lazy {
        ConcatAdapter(headerAdapter, countAndFilterAdapter, foodAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtherDishBinding.inflate(inflater)
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
        viewModel.getMenuList(kind)
    }

    private fun showRecyclerView() = with(binding) {
        layoutNoInternet.root.isVisible = false
        binding.srlOtherDish.isRefreshing = false
        srlOtherDish.isVisible = true
    }

    private fun showNoInternetConnection() = with(binding) {
        (requireView().parent as View).showSnackBar()
        layoutNoInternet.root.isVisible = true
        binding.srlOtherDish.isRefreshing = false
        srlOtherDish.isVisible = false
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
                        countAndFilterAdapter.setCount(it.list.size)
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
                    viewModel.sortType.collectLatest {
                        countAndFilterAdapter.setSortType(it)
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
        binding.srlOtherDish.setOnRefreshListener {
            initData()
        }
    }

    private fun initRecyclerView() = with(binding) {
        val decoration = ItemSpacingDecoratorWithHeader(
            spacing = 18.dp,
            spaceAdapters = listOf(foodAdapter),
            layoutDirection = GRID
        )

        rvOtherDish.adapter = concatAdapter
        rvOtherDish.addItemDecoration(decoration)
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
        rvOtherDish.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private lateinit var onDetailClick: (title: String, hash: String) -> Unit
        private lateinit var openBottomSheet: (Food) -> Unit
        private const val OTHER_KIND = "otherKind"

        fun newInstance(
            otherKind: OtherKind,
            onDetailClick: (title: String, hash: String) -> Unit,
            openBottomSheet: (Food) -> Unit
        ): OtherDishFragment {
            this.onDetailClick = onDetailClick
            this.openBottomSheet = openBottomSheet
            return OtherDishFragment().apply {
                arguments = bundleOf(OTHER_KIND to otherKind)
            }
        }
    }
}