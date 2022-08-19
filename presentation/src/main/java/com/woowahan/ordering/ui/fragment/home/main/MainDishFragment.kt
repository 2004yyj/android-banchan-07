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
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.domain.model.Menu
import com.woowahan.ordering.ui.adapter.home.FoodAdapter
import com.woowahan.ordering.ui.adapter.home.FoodAdapter.FoodItemViewType
import com.woowahan.ordering.ui.adapter.home.HeaderAdapter
import com.woowahan.ordering.ui.adapter.home.TypeAndFilterAdapter
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader.Companion.GRID
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader.Companion.VERTICAL
import com.woowahan.ordering.ui.dialog.CartBottomSheet
import com.woowahan.ordering.ui.dialog.CartDialogFragment
import com.woowahan.ordering.ui.dialog.IsExistsCartDialogFragment
import com.woowahan.ordering.ui.viewmodel.MainDishViewModel
import com.woowahan.ordering.util.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainDishFragment : Fragment() {

    private var binding: FragmentMainDishBinding? = null
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
    ): View? {
        binding = FragmentMainDishBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMenuList(Menu.Main)

        initRecyclerView()
        initFlow()
        initListener()
    }

    private fun initFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.menu.collect {
                foodAdapter.submitList(it)
            }
        }
    }

    private fun initListener() {
        foodAdapter.setOnClick(
            onDetailClick = onDetailClick,
            onCartClick = this::showCartBottomSheet
        )
    }

    private fun initRecyclerView() = with(binding!!) {
        foodAdapter = FoodAdapter()
        val headerAdapter = HeaderAdapter("모두가 좋아하는\n든든한 메인 요리")
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

    private fun setGridLayoutManager(concatAdapter: ConcatAdapter) = with(binding!!) {
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val adapter = concatAdapter.getWrappedAdapterAndPosition(position).first
                return if (adapter is FoodAdapter) 1 else 2
            }
        }
        rvMainDish.layoutManager = layoutManager
    }

    private fun setLinearLayoutManager() = with(binding!!) {
        rvMainDish.layoutManager = LinearLayoutManager(context)
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private lateinit var navigateToCart: () -> Unit

        fun newInstance(navigateToCart: () -> Unit): MainDishFragment {
            this.navigateToCart = navigateToCart
            return MainDishFragment()
        }
    }
}