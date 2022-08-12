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

    private val bestFragment = BestFragment.newInstance()
    private val mainDishFragment = MainDishFragment.newInstance()
    private val soupDishFragment = OtherDishFragment.newInstance(OtherKind.Soup)
    private val sideDishFragment = OtherDishFragment.newInstance(OtherKind.Side)

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

        initListener()
        initViewPager()
    }

    private fun initListener() {
        bestFragment.setOnDetailClick { title, hash ->

        }
        mainDishFragment.setOnDetailClick { title, hash ->

        }
        soupDishFragment.setOnDetailClick { title, hash ->

        }
        sideDishFragment.setOnDetailClick { title, hash ->

        }
    }

    private fun initViewPager() = with(binding) {
        val tabs = resources.getStringArray(R.array.tabs)

        vpHome.adapter =
            ViewPagerAdapter(this@HomeFragment, listOf(
                bestFragment,
                mainDishFragment,
                soupDishFragment,
                sideDishFragment
            ))

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