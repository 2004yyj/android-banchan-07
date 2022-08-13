package com.woowahan.ordering.ui.fragment.home.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.woowahan.ordering.databinding.FragmentOtherDishBinding
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.ui.adapter.home.CountAndFilterAdapter
import com.woowahan.ordering.ui.adapter.home.FoodAdapter
import com.woowahan.ordering.ui.adapter.home.HeaderAdapter
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader.Companion.GRID
import com.woowahan.ordering.ui.dialog.CartBottomSheet
import com.woowahan.ordering.ui.dialog.CartDialogFragment
import com.woowahan.ordering.ui.fragment.home.other.kind.OtherKind
import com.woowahan.ordering.ui.viewmodel.OtherDishViewModel
import com.woowahan.ordering.util.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtherDishFragment : Fragment() {

    private var binding: FragmentOtherDishBinding? = null
    private val viewModel by viewModels<OtherDishViewModel>()

    private val countAndFilterAdapter by lazy { CountAndFilterAdapter() }
    private val foodAdapter by lazy { FoodAdapter() }

    private lateinit var kind: OtherKind

    private var onDetailClick: (title: String, hash: String) -> Unit = { _, _ -> }
    fun setOnDetailClick(onDetailClick: (title: String, hash: String) -> Unit) {
        this.onDetailClick = onDetailClick
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtherDishBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kind = arguments?.get(OTHER_KIND) as OtherKind

        viewModel.getMenuList(kind)

        initFlow()
        initListener()
        initRecyclerView()
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
        foodAdapter.setOnClick(onDetailClick) {
            showCartBottomSheet(it)
        }
        countAndFilterAdapter.setOnItemSelectedListener {
            viewModel.getMenuList(kind, it)
        }
    }

    private fun initRecyclerView() = with(binding!!) {
        val title = when (kind) {
            OtherKind.Soup -> "정성이 담긴\n뜨끈뜨끈 국물 요리"
            OtherKind.Side -> "식탁을 풍성하게 하는\n정갈한 밑반찬"
        }
        val headerAdapter = HeaderAdapter(title)
        val concatAdapter = ConcatAdapter(headerAdapter, countAndFilterAdapter, foodAdapter)
        val layoutManager = GridLayoutManager(context, 2)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val adapter = concatAdapter.getWrappedAdapterAndPosition(position).first
                return if (adapter is FoodAdapter) 1 else 2
            }
        }
        val decoration = ItemSpacingDecoratorWithHeader(
            spacing = 18.dp,
            removeSpacePosition = listOf(0, 1),
            layoutDirection = GRID
        )

        rvOtherDish.layoutManager = layoutManager
        rvOtherDish.adapter = concatAdapter
        rvOtherDish.addItemDecoration(decoration)
    }

    private fun showCartBottomSheet(food: Food) {
        CartBottomSheet.newInstance(food) {
            showCartDialog()
        }.show(parentFragmentManager, tag)
    }

    private fun showCartDialog() {
        CartDialogFragment.newInstance {
            // TODO
            Toast.makeText(context, "장바구니 화면으로 이동", Toast.LENGTH_SHORT).show()
        }.show(parentFragmentManager, tag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val OTHER_KIND = "otherKind"

        fun newInstance(otherKind: OtherKind) = OtherDishFragment().apply {
            arguments = bundleOf(OTHER_KIND to otherKind)
        }
    }
}