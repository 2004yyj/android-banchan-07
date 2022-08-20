package com.woowahan.ordering.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.woowahan.ordering.R

class IsExistsCartDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), R.style.RoundedDialog)
            .setTitle(R.string.dialog_success_is_exists_cart)
            .setNegativeButton(R.string.dialog_check_cart) { _, _ ->
                navigateToCart()
                dismiss()
            }
            .setPositiveButton(R.string.dialog_keep_shopping) { _, _ -> dismiss() }
            .create()
    }

    companion object {
        const val TAG = "ExistDialog"
        private lateinit var navigateToCart: () -> Unit

        fun newInstance(navigateToCart: () -> Unit): IsExistsCartDialogFragment {
            this.navigateToCart = navigateToCart
            return IsExistsCartDialogFragment()
        }
    }
}