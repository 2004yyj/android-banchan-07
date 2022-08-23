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
import com.woowahan.ordering.ui.uistate.ListUiState
import com.woowahan.ordering.ui.viewmodel.OtherDishViewModel
import com.woowahan.ordering.util.dp
import com.woowahan.ordering.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OtherDishFragment : Fragment() {

    private var _binding: FragmentOtherDishBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel by viewModels<OtherDishViewModel>()

    private val countAndFilterAdapter by lazy {
        CountAndFilterAdapter(
            onItemSelected = {
                viewModel.sortType = it
                initData()
            }
        )
    }
    private lateinit var foodAdapter: FoodAdapter
    private val kind by lazy { requireArguments().get(OTHER_KIND) as OtherKind }

    private var onDetailClick: (title: String, hash: String) -> Unit = { _, _ -> }
    fun setOnDetailClick(onDetailClick: (title: String, hash: String) -> Unit) {
        this.onDetailClick = onDetailClick
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
        initFlow()
        initListener()
        initRecyclerView()
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
        requireContext().showToast(getString(R.string.no_internet_message))
        layoutNoInternet.root.isVisible = true
        binding.srlOtherDish.isRefreshing = false
        srlOtherDish.isVisible = false
    }

    private fun initFlow() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(
                lifecycle = lifecycle,
                minActiveState = Lifecycle.State.STARTED
            ).collect {
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
    }

    private fun initListener() {
        binding.layoutNoInternet.btnRetry.setOnClickListener {
            initData()
        }
        binding.srlOtherDish.setOnRefreshListener {
            initData()
        }
    }

    private fun initRecyclerView() = with(binding) {
        val title = when (kind) {
            OtherKind.Soup -> getString(R.string.main_header_soup)
            OtherKind.Side -> getString(R.string.main_header_side)
        }
        val headerAdapter = HeaderAdapter(title)
        foodAdapter = FoodAdapter(
            onDetailClick = onDetailClick,
            onCartClick = openBottomSheet
        )
        val concatAdapter = ConcatAdapter(headerAdapter, countAndFilterAdapter, foodAdapter)
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
        private lateinit var openBottomSheet: (Food) -> Unit
        private const val OTHER_KIND = "otherKind"

        fun newInstance(otherKind: OtherKind, openBottomSheet: (Food) -> Unit): OtherDishFragment {
            this.openBottomSheet = openBottomSheet
            return OtherDishFragment().apply {
                arguments = bundleOf(OTHER_KIND to otherKind)
            }
        }
    }
}