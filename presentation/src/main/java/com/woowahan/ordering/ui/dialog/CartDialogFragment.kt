package com.woowahan.ordering.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.woowahan.ordering.R

class CartDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), R.style.RoundedDialog)
            .setTitle(R.string.dialog_success_cart_add)
            .setNegativeButton(R.string.dialog_check_cart) { _, _ ->
                navigateToCart()
                dismiss()
            }
            .setPositiveButton(R.string.dialog_keep_shopping) { _, _ -> dismiss() }
            .create()
    }

    companion object {
        private lateinit var navigateToCart: () -> Unit

        fun newInstance(navigateToCart: () -> Unit): CartDialogFragment {
            this.navigateToCart = navigateToCart
            return CartDialogFragment()
        }
    }
}