package com.woowahan.ordering.ui.fragment.home.best

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.FragmentBestBinding
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.ui.adapter.home.BestFoodAdapter
import com.woowahan.ordering.ui.adapter.home.HeaderAdapter
import com.woowahan.ordering.ui.viewmodel.BestViewModel
import com.woowahan.ordering.util.hasNetwork
import com.woowahan.ordering.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BestFragment : Fragment() {

    private val viewModel: BestViewModel by viewModels()
    private var _binding: FragmentBestBinding? = null
    private val binding get() = requireNotNull(_binding)
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
    ): View {
        _binding = FragmentBestBinding.inflate(inflater)
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
            viewModel.getBestList()
            layoutNoInternet.root.isVisible = false
            rvBest.isVisible = true
        } else {
            requireContext().showToast(getString(R.string.no_internet_message))
            layoutNoInternet.root.isVisible = true
            rvBest.isVisible = false
        }
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
            onCartClick = openBottomSheet
        )
        binding.layoutNoInternet.btnRetry.setOnClickListener {
            initData()
        }
    }

    private fun initRecyclerView() = with(binding) {
        val headerAdapter = HeaderAdapter(
            getString(R.string.main_header_best),
            getString(R.string.main_header_best_chip)
        )
        rvBest.adapter = ConcatAdapter(headerAdapter, adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private lateinit var openBottomSheet: (Food) -> Unit

        fun newInstance(openBottomSheet: (Food) -> Unit): BestFragment {
            this.openBottomSheet = openBottomSheet
            return BestFragment()
        }
    }
}