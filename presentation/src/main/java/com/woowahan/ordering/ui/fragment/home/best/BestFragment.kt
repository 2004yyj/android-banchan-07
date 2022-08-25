package com.woowahan.ordering.ui.fragment.home.best

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.FragmentBestBinding
import com.woowahan.ordering.domain.model.Best
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.ui.adapter.home.BestFoodAdapter
import com.woowahan.ordering.ui.adapter.home.HeaderAdapter
import com.woowahan.ordering.ui.uistate.ListUiState
import com.woowahan.ordering.ui.viewmodel.BestViewModel
import com.woowahan.ordering.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BestFragment : Fragment() {

    private val viewModel: BestViewModel by viewModels()
    private var _binding: FragmentBestBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val adapter: BestFoodAdapter by lazy {
        BestFoodAdapter(onDetailClick, openBottomSheet)
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

    private fun initData() {
        viewModel.getBestList()
    }

    private fun showRecyclerView() = with(binding) {
        layoutNoInternet.root.isVisible = false
        binding.srlBest.isRefreshing = false
        srlBest.isVisible = true
    }

    private fun showNoInternetConnection() = with(binding) {
        requireContext().showToast(getString(R.string.no_internet_message))
        layoutNoInternet.root.isVisible = true
        binding.srlBest.isRefreshing = false
        srlBest.isVisible = false
    }

    private fun initFlow() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(
                lifecycle = lifecycle,
                minActiveState = Lifecycle.State.STARTED
            ).collectLatest {
                when (it) {
                    is ListUiState.Refreshing -> {}
                    is ListUiState.List<Best> -> {
                        showRecyclerView()
                        adapter.submitList(it.list)
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
        binding.srlBest.setOnRefreshListener {
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
        private lateinit var onDetailClick: (title: String, hash: String) -> Unit
        private lateinit var openBottomSheet: (Food) -> Unit

        fun newInstance(
            onDetailClick: (title: String, hash: String) -> Unit,
            openBottomSheet: (Food) -> Unit
        ): BestFragment {
            this.onDetailClick = onDetailClick
            this.openBottomSheet = openBottomSheet
            return BestFragment()
        }
    }
}