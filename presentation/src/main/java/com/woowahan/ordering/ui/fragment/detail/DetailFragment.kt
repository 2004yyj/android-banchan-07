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
import com.woowahan.ordering.ui.adapter.detail.DetailImagesFooterAdapter
import com.woowahan.ordering.ui.adapter.detail.DetailInfoAdapter
import com.woowahan.ordering.ui.adapter.detail.DetailThumbImagesAdapter
import com.woowahan.ordering.ui.dialog.CartDialogFragment
import com.woowahan.ordering.ui.dialog.IsExistsCartDialogFragment
import com.woowahan.ordering.ui.fragment.cart.CartFragment
import com.woowahan.ordering.ui.uistate.DetailUiState
import com.woowahan.ordering.util.replace
import com.woowahan.ordering.ui.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private var binding: FragmentDetailBinding? = null

    private lateinit var hash: String
    private lateinit var title: String

    private lateinit var detailThumbImagesAdapter: DetailThumbImagesAdapter
    private lateinit var detailInfoAdapter: DetailInfoAdapter
    private lateinit var detailImagesFooterAdapter: DetailImagesFooterAdapter

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
                    viewModel.insertRecently(title, it)
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
            replaceToCart()
        }.show(parentFragmentManager, tag)
    }

    private fun showIsExistsCartDialog() {
        IsExistsCartDialogFragment.newInstance {
            replaceToCart()
        }.show(parentFragmentManager, tag)
    }

    private fun initArguments() = with(requireArguments()) {
        hash = getString(HASH, "")
        title = getString(TITLE, "")
    }

    private fun initRecyclerView() = with(binding!!) {
        detailThumbImagesAdapter = DetailThumbImagesAdapter()
        detailInfoAdapter = DetailInfoAdapter(viewModel, title)
        detailImagesFooterAdapter = DetailImagesFooterAdapter()
        val adapter = ConcatAdapter(detailThumbImagesAdapter, detailInfoAdapter, detailImagesFooterAdapter)
        rvDetail.adapter = adapter
    }

    private fun replaceToCart() {
        parentFragmentManager.replace(
            CartFragment::class.java,
            (requireView().parent as View).id,
            "Cart"
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val TITLE = "title"
        const val HASH = "hash"
    }
}