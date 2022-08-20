package com.woowahan.ordering.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.BottomSheetCartBinding
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.ui.uistate.CartBottomSheetUiState
import com.woowahan.ordering.ui.viewmodel.CartBottomSheetViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartBottomSheet : BottomSheetDialogFragment() {

    private var binding: BottomSheetCartBinding? = null
    private val viewModel by viewModels<CartBottomSheetViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_cart, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let {
            it.cart = cart
            it.vm = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }

        initFlow()
    }

    private fun initFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is CartBottomSheetUiState.Loading -> {
                            showProgressBar()
                        }
                        is CartBottomSheetUiState.Success -> {
                            hideProgressBar()
                            onAddAction()
                            dialog?.dismiss()
                        }
                        is CartBottomSheetUiState.Error -> {
                            hideProgressBar()
                            showError(uiState.exception)
                        }
                        is CartBottomSheetUiState.Finished -> {
                            hideProgressBar()
                            dialog?.dismiss()
                        }
                    }
                }
            }
        }
    }

    private fun showProgressBar() {
        binding?.let {
            it.pbLoading.visibility = View.VISIBLE
        }
    }

    private fun hideProgressBar() {
        binding?.let {
            it.pbLoading.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val TAG = "CartBottomSheet"
        private lateinit var cart: Cart
        private lateinit var onAddAction: () -> Unit

        fun newInstance(cart: Cart, onAddAction: () -> Unit): CartBottomSheet {
            this.cart = cart
            this.onAddAction = onAddAction
            return CartBottomSheet()
        }
    }
}