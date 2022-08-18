package com.woowahan.ordering.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.tabs.TabLayoutMediator
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.FragmentHomeBinding
import com.woowahan.ordering.ui.adapter.home.ViewPagerAdapter
import com.woowahan.ordering.ui.fragment.cart.CartFragment
import com.woowahan.ordering.ui.fragment.detail.DetailFragment
import com.woowahan.ordering.ui.fragment.detail.DetailFragment.Companion.HASH
import com.woowahan.ordering.ui.fragment.detail.DetailFragment.Companion.TITLE
import com.woowahan.ordering.ui.fragment.home.best.BestFragment
import com.woowahan.ordering.ui.fragment.home.main.MainDishFragment
import com.woowahan.ordering.ui.fragment.home.other.OtherDishFragment
import com.woowahan.ordering.ui.fragment.home.other.kind.OtherKind
import com.woowahan.ordering.util.replace

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null

    private val bestFragment = BestFragment.newInstance { replaceToCart() }
    private val mainDishFragment = MainDishFragment.newInstance { replaceToCart() }
    private val soupDishFragment = OtherDishFragment.newInstance(OtherKind.Soup) { replaceToCart() }
    private val sideDishFragment = OtherDishFragment.newInstance(OtherKind.Side) { replaceToCart() }

    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initViewPager()
    }

    private fun initListener() {
        bestFragment.setOnDetailClick(this::replaceToDetail)
        mainDishFragment.setOnDetailClick(this::replaceToDetail)
        soupDishFragment.setOnDetailClick(this::replaceToDetail)
        sideDishFragment.setOnDetailClick(this::replaceToDetail)
    }

    private fun replaceToDetail(title: String, hash: String) {
        parentFragmentManager.replace(
            DetailFragment::class.java,
            (requireView().parent as View).id,
            "Detail",
            bundleOf(TITLE to title, HASH to hash)
        )
    }

    private fun replaceToCart() {
        parentFragmentManager.replace(
            CartFragment::class.java,
            (requireView().parent as View).id,
            "Cart"
        )
    }

    private fun initViewPager() = with(binding!!) {
        val tabs = resources.getStringArray(R.array.tabs)

        vpHome.adapter =
            ViewPagerAdapter(
                this@HomeFragment, listOf(
                    bestFragment,
                    mainDishFragment,
                    soupDishFragment,
                    sideDishFragment
                )
            )

        tabLayoutMediator = TabLayoutMediator(tlHome, vpHome) { tab, position ->
            tab.text = tabs[position]
        }
        tabLayoutMediator.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onDestroy() {
        tabLayoutMediator.detach()
        super.onDestroy()
    }
}