package com.woowahan.ordering.ui.fragment.home.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.woowahan.ordering.databinding.FragmentMainDishBinding
import com.woowahan.ordering.domain.model.Menu
import com.woowahan.ordering.ui.adapter.home.FoodAdapter
import com.woowahan.ordering.ui.adapter.home.FoodAdapter.FoodItemViewType
import com.woowahan.ordering.ui.adapter.home.HeaderAdapter
import com.woowahan.ordering.ui.adapter.home.TypeAndFilterAdapter
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader.Companion.GRID
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader.Companion.VERTICAL
import com.woowahan.ordering.ui.dialog.CartBottomSheet
import com.woowahan.ordering.ui.viewmodel.MainDishViewModel
import com.woowahan.ordering.util.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainDishFragment(
    private val onDetailClick: (title: String, hash: String) -> Unit
) : Fragment() {

    private lateinit var cartBottomSheet: CartBottomSheet

    private lateinit var binding: FragmentMainDishBinding
    private val viewModel by viewModels<MainDishViewModel>()

    private val typeAndFilterAdapter by lazy { TypeAndFilterAdapter() }
    private val foodAdapter by lazy { FoodAdapter() }

    private val gridDecoration = ItemSpacingDecoratorWithHeader(
        spacing = 18.dp,
        removeSpacePosition = listOf(0, 1),
        GRID
    )

    private val linearDecoration = ItemSpacingDecoratorWithHeader(
        spacing = 18.dp,
        removeSpacePosition = listOf(0, 1),
        VERTICAL
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainDishBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMenuList(Menu.Main)

        initFlow()
        initListener()
        initRecyclerView()
    }

    private fun initFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.menu.collect {
                foodAdapter.submitList(it)
            }
        }
    }

    private fun initListener() {
        foodAdapter.setOnClick(onDetailClick) {
            cartBottomSheet = CartBottomSheet(it)
            cartBottomSheet.show(parentFragmentManager, "Main")
        }
    }

    private fun initRecyclerView() = with(binding) {
        val headerAdapter = HeaderAdapter("모두가 좋아하는\n든든한 메인 요리")
        val concatAdapter = ConcatAdapter(headerAdapter, typeAndFilterAdapter, foodAdapter)

        typeAndFilterAdapter.setOnItemSelected {
            
        }

        typeAndFilterAdapter.setOnListTypeChangeClicked {
            if (!it) {
                foodAdapter.viewTypeChange(FoodItemViewType.GridItem)
                rvMainDish.removeItemDecoration(linearDecoration)
                rvMainDish.addItemDecoration(gridDecoration)
                setGridLayoutManager(concatAdapter)
            }
            else {
                foodAdapter.viewTypeChange(FoodItemViewType.LinearItem)
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

    companion object {
        fun newInstance(onDetailClick: (title: String, hash: String) -> Unit) =
            MainDishFragment(onDetailClick)
    }
}