package com.woowahan.ordering.ui.fragment.home.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import com.woowahan.ordering.ui.viewmodel.OtherDishViewModel
import com.woowahan.ordering.util.dp
import com.woowahan.ordering.util.hasNetwork
import com.woowahan.ordering.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtherDishFragment : Fragment() {

    private var _binding: FragmentOtherDishBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel by viewModels<OtherDishViewModel>()

    private val countAndFilterAdapter by lazy { CountAndFilterAdapter() }
    private val foodAdapter by lazy { FoodAdapter() }
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

    private fun initData() = with(binding) {
        if (requireContext().hasNetwork()) {
            viewModel.getMenuList(kind)
            layoutNoInternet.root.isVisible = false
            rvOtherDish.isVisible = true
        } else {
            requireContext().showToast(getString(R.string.no_internet_message))
            layoutNoInternet.root.isVisible = true
            rvOtherDish.isVisible = false
        }
    }

    private fun initFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.menu.collect {
                countAndFilterAdapter.setCount(it.size)
                foodAdapter.submitList(it)
            }
        }
    }

    private fun initListener() {
        foodAdapter.setOnClick(
            onDetailClick = onDetailClick,
            onCartClick = openBottomSheet
        )
        countAndFilterAdapter.setOnItemSelectedListener {
            viewModel.getMenuList(kind, it)
        }
        binding.layoutNoInternet.btnRetry.setOnClickListener {
            initData()
        }
    }

    private fun initRecyclerView() = with(binding) {
        val title = when (kind) {
            OtherKind.Soup -> getString(R.string.main_header_soup)
            OtherKind.Side -> getString(R.string.main_header_side)
        }
        val headerAdapter = HeaderAdapter(title)
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