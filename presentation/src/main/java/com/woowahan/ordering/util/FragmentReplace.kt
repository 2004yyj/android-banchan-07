package com.woowahan.ordering.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun <T: Fragment> FragmentManager.add(
    fragmentClass: Class<T>,
    containerViewId: Int,
    tag: String,
    arguments: Bundle = Bundle()
): Fragment {
    return beginTransaction().run {
        val constructor = fragmentClass.getConstructor()
        val fragment: Fragment = findFragmentByTag(tag) ?: constructor.newInstance()
        fragment.arguments = arguments
        if (findFragmentByTag(tag) == null)
            add(containerViewId, fragment, tag)
        commit()
        fragment
    }
}

fun <T: Fragment> FragmentManager.replace(
    fragmentClass: Class<T>,
    containerViewId: Int,
    tag: String,
    arguments: Bundle = Bundle()
): Fragment {
    return beginTransaction().run {
        val constructor = fragmentClass.getConstructor()
        val fragment: Fragment = findFragmentByTag(tag) ?: constructor.newInstance()
        fragment.arguments = arguments
        replace(containerViewId, fragment, tag)
        addToBackStack(tag)
        commit()
        fragment
    }
}

fun <T : Fragment> FragmentManager.replaceWithPopBackstack(
    fragmentClass: Class<T>,
    containerViewId: Int,
    tag: String,
    arguments: Bundle = Bundle()
): Fragment {
    return beginTransaction().run {
        val constructor = fragmentClass.getConstructor()
        val fragment: Fragment = findFragmentByTag(tag) ?: constructor.newInstance()
        fragment.arguments = arguments
        popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        replace(containerViewId, fragment, tag)
        addToBackStack(tag)
        commit()
        fragment
    }
}

fun FragmentManager.clearAllBackStack(tag: String? = null) {
    popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}