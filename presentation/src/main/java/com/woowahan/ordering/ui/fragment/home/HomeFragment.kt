package com.woowahan.ordering.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.FragmentHomeBinding
import com.woowahan.ordering.ui.adapter.home.ViewPagerAdapter
import com.woowahan.ordering.ui.fragment.home.best.BestFragment
import com.woowahan.ordering.ui.fragment.home.main.MainDishFragment
import com.woowahan.ordering.ui.fragment.home.other.OtherDishFragment
import com.woowahan.ordering.ui.fragment.home.other.kind.OtherKind

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val bestFragment = BestFragment.newInstance { title, hash ->
        // BestFragment 에서 디테일 클릭 시
    }

    private val mainDishFragment = MainDishFragment.newInstance { title, hash ->
        // MainDishFragment 에서 디테일 클릭 시
    }

    private val soupDishFragment = OtherDishFragment.newInstance({ title, hash ->
        // SoupDishFragment 에서 디테일 클릭 시
    }, OtherKind.Soup)

    private val sideDishFragment = OtherDishFragment.newInstance({ title, hash ->
        // SideDishFragment 에서 디테일 클릭 시
    }, OtherKind.Side)

    private val adapter by lazy {
        ViewPagerAdapter(this, listOf(
            bestFragment,
            mainDishFragment,
            soupDishFragment,
            sideDishFragment
        ))
    }

    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
    }

    private fun initViewPager() = with(binding) {
        val tabs = resources.getStringArray(R.array.tabs)

        vpHome.adapter = adapter
        tabLayoutMediator = TabLayoutMediator(tlHome, vpHome) { tab, position ->
            tab.text = tabs[position]
        }
        tabLayoutMediator.attach()
    }

    override fun onDestroy() {
        tabLayoutMediator.detach()
        super.onDestroy()
    }
}