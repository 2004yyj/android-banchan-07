package com.woowahan.ordering.ui.fragment.home.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.woowahan.ordering.databinding.FragmentMainDishBinding
import com.woowahan.ordering.domain.model.Menu
import com.woowahan.ordering.ui.adapter.home.FoodLinearAdapter
import com.woowahan.ordering.ui.adapter.home.HeaderAdapter
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
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

    private val foodAdapter by lazy { FoodLinearAdapter() }

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
        val concatAdapter = ConcatAdapter(headerAdapter, foodAdapter)
        val decoration = ItemSpacingDecoratorWithHeader(
            spacing = 18.dp,
            removeSpacePosition = listOf(0),
            VERTICAL
        )
        rvMainDish.adapter = concatAdapter
        rvMainDish.addItemDecoration(decoration)
    }

    companion object {
        fun newInstance(onDetailClick: (title: String, hash: String) -> Unit) =
            MainDishFragment(onDetailClick)
    }
}