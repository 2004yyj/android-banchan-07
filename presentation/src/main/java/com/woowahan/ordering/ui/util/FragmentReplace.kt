package com.woowahan.ordering.ui.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.util.function.Function

fun <T: Fragment> FragmentManager.add(
    fragmentClass: Class<T>,
    containerViewId: Int,
    tag: String,
    arguments: Bundle = Bundle()
) {
    beginTransaction().apply {
        val constructor = fragmentClass.getConstructor()
        val fragment: Fragment = findFragmentByTag(tag) ?: constructor.newInstance()
        fragment.arguments = arguments
        if (findFragmentByTag(tag) == null)
            add(containerViewId, fragment, tag)
        commit()
    }
}

fun <T: Fragment> FragmentManager.replace(
    fragmentClass: Class<T>,
    containerViewId: Int,
    tag: String,
    arguments: Bundle = Bundle()
) {
    beginTransaction().apply {
        val constructor = fragmentClass.getConstructor()
        val fragment: Fragment = findFragmentByTag(tag) ?: constructor.newInstance()
        fragment.arguments = arguments
        replace(containerViewId, fragment, tag)
        if (findFragmentByTag(tag) == null) {
            addToBackStack(tag)
        }
        commit()
    }
}