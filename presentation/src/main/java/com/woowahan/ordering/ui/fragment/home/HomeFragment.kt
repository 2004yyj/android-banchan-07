package com.woowahan.ordering.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.woowahan.ordering.R
import com.woowahan.ordering.data.mapper.toCartModel
import com.woowahan.ordering.databinding.FragmentHomeBinding
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.ui.adapter.home.ViewPagerAdapter
import com.woowahan.ordering.ui.dialog.CartBottomSheet
import com.woowahan.ordering.ui.dialog.CartDialogFragment
import com.woowahan.ordering.ui.dialog.IsExistsCartDialogFragment
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

    private lateinit var bestFragment: BestFragment
    private lateinit var mainDishFragment: MainDishFragment
    private lateinit var soupDishFragment: OtherDishFragment
    private lateinit var sideDishFragment: OtherDishFragment
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

        initFragment()
        initViewPager()
    }

    private fun initFragment() {
        bestFragment = BestFragment.newInstance(
            onDetailClick = this::replaceToDetail,
            openBottomSheet = this::showCartBottomSheet
        )
        mainDishFragment = MainDishFragment.newInstance(
            onDetailClick = this::replaceToDetail,
            openBottomSheet = this::showCartBottomSheet
        )
        soupDishFragment = OtherDishFragment.newInstance(
            OtherKind.Soup,
            onDetailClick = this::replaceToDetail,
            openBottomSheet = this::showCartBottomSheet
        )
        sideDishFragment = OtherDishFragment.newInstance(
            OtherKind.Side,
            onDetailClick = this::replaceToDetail,
            openBottomSheet = this::showCartBottomSheet
        )
    }

    private fun replaceToDetail(title: String, hash: String) {
        parentFragmentManager.replace(
            DetailFragment::class.java,
            (requireView().parent as View).id,
            DetailFragment.TAG,
            bundleOf(TITLE to title, HASH to hash)
        )
    }

    private fun replaceToCart() {
        parentFragmentManager.replace(
            CartFragment::class.java,
            (requireView().parent as View).id,
            CartFragment.TAG
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

    private fun showCartBottomSheet(food: Food) {
        if (food.isAdded) {
            IsExistsCartDialogFragment.newInstance {
                replaceToCart()
            }.show(parentFragmentManager, IsExistsCartDialogFragment.TAG)
        } else {
            CartBottomSheet.newInstance(food.toCartModel()) {
                showCartDialog()
            }.show(parentFragmentManager, CartBottomSheet.TAG)
        }
    }

    private fun showCartDialog() {
        CartDialogFragment.newInstance {
            replaceToCart()
        }.show(parentFragmentManager, CartDialogFragment.TAG)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.vpHome?.let {
            it.adapter = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val TAG = "Home"
    }
}