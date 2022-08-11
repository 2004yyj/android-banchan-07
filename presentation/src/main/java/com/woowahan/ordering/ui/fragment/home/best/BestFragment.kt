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
import com.woowahan.ordering.ui.adapter.home.BestFoodAdapter
import com.woowahan.ordering.ui.adapter.home.HeaderAdapter
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader.Companion.VERTICAL
import com.woowahan.ordering.ui.viewmodel.BestViewModel
import com.woowahan.ordering.util.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BestFragment(
    private val onDetailClick: (title: String, hash: String) -> Unit
) : Fragment() {

    private val viewModel: BestViewModel by viewModels()
    private lateinit var binding: FragmentBestBinding
    private val adapter: BestFoodAdapter by lazy {
        BestFoodAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBestBinding.inflate(inflater)
        return binding.root
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
        adapter.setOnClick(onDetailClick)
    }

    private fun initRecyclerView() = with(binding) {
        val headerAdapter = HeaderAdapter("한 번 주문하면\n두 번 반하는 반찬들", "기획전")
        rvBest.adapter = ConcatAdapter(headerAdapter, adapter)
    }

    companion object {
        fun newInstance(onDetailClick: (title: String, hash: String) -> Unit) =
            BestFragment(onDetailClick)
    }
}