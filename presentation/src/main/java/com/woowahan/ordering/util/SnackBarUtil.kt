package com.woowahan.ordering.util

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.woowahan.ordering.R

fun View.showSnackBar() {
    Snackbar.make(
        this,
        R.string.no_internet_message,
        Snackbar.LENGTH_SHORT
    ).show()
}