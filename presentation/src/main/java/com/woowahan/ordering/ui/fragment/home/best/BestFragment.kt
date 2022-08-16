package com.woowahan.ordering.ui.fragment.home.best

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.woowahan.ordering.databinding.FragmentBestBinding
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.ui.adapter.home.BestFoodAdapter
import com.woowahan.ordering.ui.adapter.home.HeaderAdapter
import com.woowahan.ordering.ui.dialog.CartBottomSheet
import com.woowahan.ordering.ui.dialog.CartDialogFragment
import com.woowahan.ordering.ui.viewmodel.BestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BestFragment : Fragment() {

    private val viewModel: BestViewModel by viewModels()
    private var binding: FragmentBestBinding? = null
    private val adapter: BestFoodAdapter by lazy {
        BestFoodAdapter()
    }

    private var onDetailClick: (title: String, hash: String) -> Unit = { _, _ -> }
    fun setOnDetailClick(onDetailClick: (title: String, hash: String) -> Unit) {
        this.onDetailClick = onDetailClick
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBestBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBestList()

        initFlow()
        initListener()
        initRecyclerView()
    }

    private fun initFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.best.collect {
                adapter.submitList(it)
            }
        }
    }

    private fun initListener() {
        adapter.setOnClick(
            onDetailClick = onDetailClick,
            onCartClick = { listPosition, itemPosition, food ->
                showCartBottomSheet(listPosition, itemPosition, food)
            }
        )
    }

    private fun initRecyclerView() = with(binding!!) {
        val headerAdapter = HeaderAdapter("한 번 주문하면\n두 번 반하는 반찬들", "기획전")
        rvBest.adapter = ConcatAdapter(headerAdapter, adapter)
    }

    private fun showCartBottomSheet(listPosition: Int, itemPosition: Int, food: Food) {
        CartBottomSheet.newInstance(food) {
            updateFoodState(listPosition, itemPosition)
            showCartDialog()
        }.show(parentFragmentManager, tag)
    }

    private fun showCartDialog() {
        CartDialogFragment.newInstance {
            navigateToCart()
        }.show(parentFragmentManager, tag)
    }

    private fun updateFoodState(listPosition: Int, itemPosition: Int) {
        adapter.currentList[listPosition].items[itemPosition].isAdded = true
        adapter.notifyItemChanged(listPosition)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        lateinit var navigateToCart: () -> Unit

        fun newInstance(navigateToCart: () -> Unit) : BestFragment {
            this.navigateToCart = navigateToCart
            return BestFragment()
        }
    }
}