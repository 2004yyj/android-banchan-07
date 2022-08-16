package com.woowahan.ordering.ui.fragment.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import com.woowahan.ordering.databinding.FragmentDetailBinding
import com.woowahan.ordering.ui.UiState
import com.woowahan.ordering.ui.adapter.detail.DetailImagesFooterAdapter
import com.woowahan.ordering.ui.adapter.detail.DetailInfoAdapter
import com.woowahan.ordering.ui.adapter.detail.DetailThumbImagesAdapter
import com.woowahan.ordering.ui.dialog.CartDialogFragment
import com.woowahan.ordering.ui.dialog.IsExistsCartDialogFragment
import com.woowahan.ordering.ui.fragment.home.HomeFragment.Companion.HASH
import com.woowahan.ordering.ui.fragment.home.HomeFragment.Companion.TITLE
import com.woowahan.ordering.ui.uistate.DetailUiState
import com.woowahan.ordering.ui.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private var binding: FragmentDetailBinding? = null

    private lateinit var hash: String
    private lateinit var title: String

    private val detailThumbImagesAdapter by lazy { DetailThumbImagesAdapter() }
    private val detailInfoAdapter by lazy { DetailInfoAdapter(viewModel, title) }
    private val detailImagesFooterAdapter by lazy { DetailImagesFooterAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFlow()
        initArguments()
        initRecyclerView()

        viewModel.getFoodDetail(hash)
    }

    private fun initFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.foodDetail.collect {
                it?.let {
                    detailThumbImagesAdapter.submitList(it.thumbImages)
                    detailImagesFooterAdapter.submitList(it.detailSection)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is DetailUiState.Success -> {
                            showCartDialog()
                        }
                        is DetailUiState.Error -> {
                            showError(uiState.exception)
                        }
                        is DetailUiState.CartExist -> {
                            showIsExistsCartDialog()
                        }
                    }
                }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showCartDialog() {
        parentFragmentManager.popBackStack()
        
        CartDialogFragment.newInstance {
            // TODO
            Toast.makeText(context, "장바구니 화면으로 이동", Toast.LENGTH_SHORT).show()
        }.show(parentFragmentManager, tag)
    }

    private fun showIsExistsCartDialog() {
        IsExistsCartDialogFragment.newInstance {
            // TODO
            Toast.makeText(context, "장바구니 화면으로 이동", Toast.LENGTH_SHORT).show()
        }.show(parentFragmentManager, tag)
    }

    private fun initArguments() = with(requireArguments()) {
        hash = getString(HASH, "")
        title = getString(TITLE, "")
    }

    private fun initRecyclerView() = with(binding!!) {
        val adapter = ConcatAdapter(detailThumbImagesAdapter, detailInfoAdapter, detailImagesFooterAdapter)
        rvDetail.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}