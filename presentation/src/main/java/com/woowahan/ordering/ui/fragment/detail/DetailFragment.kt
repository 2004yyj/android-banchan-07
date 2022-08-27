package com.woowahan.ordering.ui.fragment.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import com.woowahan.ordering.databinding.FragmentDetailBinding
import com.woowahan.ordering.ui.adapter.detail.DetailImagesFooterAdapter
import com.woowahan.ordering.ui.adapter.detail.DetailInfoAdapter
import com.woowahan.ordering.ui.adapter.detail.DetailThumbImagesAdapter
import com.woowahan.ordering.ui.dialog.CartDialogFragment
import com.woowahan.ordering.ui.dialog.IsExistsCartDialogFragment
import com.woowahan.ordering.ui.fragment.cart.CartFragment
import com.woowahan.ordering.ui.listener.setOnThrottleClickListener
import com.woowahan.ordering.ui.uistate.DetailUiState
import com.woowahan.ordering.ui.viewmodel.DetailViewModel
import com.woowahan.ordering.util.hasNetwork
import com.woowahan.ordering.util.replaceWithPopBackstack
import com.woowahan.ordering.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val hash by lazy { requireArguments().getString(HASH, "") }
    private val title by lazy { requireArguments().getString(TITLE, "") }

    private lateinit var detailThumbImagesAdapter: DetailThumbImagesAdapter
    private lateinit var detailInfoAdapter: DetailInfoAdapter
    private lateinit var detailImagesFooterAdapter: DetailImagesFooterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initViews()
        initFlow()
    }

    private fun initData() {
        if (requireContext().hasNetwork()) {
            viewModel.getFoodDetail(hash)
            showRecyclerView()
        } else {
            requireView().showSnackBar()
            hideRecyclerView()
        }
        viewModel.init()
    }

    private fun showRecyclerView() = with(binding) {
        layoutNoInternet.root.isVisible = false
        rvDetail.isVisible = true
    }

    private fun hideRecyclerView() = with(binding) {
        layoutNoInternet.root.isVisible = true
        rvDetail.isVisible = false
    }

    private fun initViews() {
        binding.layoutNoInternet.btnRetry.setOnThrottleClickListener {
            initData()
        }
        initRecyclerView()
    }

    private fun initRecyclerView() = with(binding) {
        detailThumbImagesAdapter = DetailThumbImagesAdapter()
        detailInfoAdapter = DetailInfoAdapter(viewModel, title)
        detailImagesFooterAdapter = DetailImagesFooterAdapter()

        rvDetail.scrollToPosition(0)
        rvDetail.adapter = ConcatAdapter(
            detailThumbImagesAdapter,
            detailInfoAdapter,
            detailImagesFooterAdapter
        )
    }

    private fun initFlow() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.foodDetail.collect {
                it?.let {
                    detailThumbImagesAdapter.submitList(it.thumbImages)
                    detailImagesFooterAdapter.submitList(it.detailSection)
                    viewModel.insertRecently(title, it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
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
        CartDialogFragment.newInstance {
            replaceToCart()
        }.show(parentFragmentManager, tag)
    }

    private fun showIsExistsCartDialog() {
        IsExistsCartDialogFragment.newInstance {
            replaceToCart()
        }.show(parentFragmentManager, tag)
    }

    private fun replaceToCart() {
        parentFragmentManager.replaceWithPopBackstack(
            CartFragment::class.java,
            (requireView().parent as View).id,
            CartFragment.TAG
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TITLE = "title"
        const val HASH = "hash"
        const val TAG = "Detail"
    }
}