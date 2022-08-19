package com.woowahan.ordering.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.ActionCartBinding
import com.woowahan.ordering.databinding.ActionOrderBinding
import com.woowahan.ordering.databinding.ActivityMainBinding
import com.woowahan.ordering.ui.fragment.cart.CartFragment
import com.woowahan.ordering.ui.fragment.cart.recently.RecentlyViewedFragment
import com.woowahan.ordering.ui.fragment.detail.DetailFragment
import com.woowahan.ordering.ui.fragment.home.HomeFragment
import com.woowahan.ordering.ui.fragment.order.OrderListFragment
import com.woowahan.ordering.ui.fragment.order.detail.OrderDetailFragment
import com.woowahan.ordering.ui.receiver.CartReceiver
import com.woowahan.ordering.util.add
import com.woowahan.ordering.util.replace
import com.woowahan.ordering.ui.viewmodel.MainViewModel
import com.woowahan.ordering.util.replaceWithPopBackstack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cartBinding: ActionCartBinding
    private lateinit var orderBinding: ActionOrderBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        cartBinding = ActionCartBinding.inflate(layoutInflater)
        orderBinding = ActionOrderBinding.inflate(layoutInflater)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initFlow()
        initToolbar()
        initListener()
        initFragment()

        viewModel.getCartSize()

        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent = intent
    }

    private fun initFragment() = with(binding) {
        supportFragmentManager.add(
            HomeFragment::class.java,
            binding.fcvMain.id,
            HomeFragment.TAG
        )
        val deliveryTime = intent.getLongExtra(CartReceiver.DELIVERY_FINISHED_TIME, 0L)
        if (deliveryTime != 0L) replaceToOrderDetail(deliveryTime)
    }

    private fun initToolbar() = with(binding) {
        toolbarHome.menu.findItem(R.id.item_cart).actionView = cartBinding.root
        toolbarHome.menu.findItem(R.id.item_order).actionView = orderBinding.root
    }

    private fun initListener() = with(binding) {
        cartBinding.ibtCart.setOnClickListener {
            replaceToCart()
        }
        orderBinding.ibtOrder.setOnClickListener {
            replaceToOrderList()
        }
        toolbarCart.setNavigationOnClickListener {
            supportFragmentManager.popBackStack()
        }
        supportFragmentManager.addOnBackStackChangedListener {
            supportFragmentManager.fragments.forEach {
                if (it.isVisible) changeToolbar(it)
            }
        }
    }

    private fun initFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.cartSize.collect {
                cartBinding.count = it
            }
        }
    }

    private fun changeToolbar(fragment: Fragment) = with(binding) {
        when (fragment) {
            is OrderDetailFragment -> {
                toolbarHome.isVisible = false
                toolbarCart.isVisible = false
            }
            is CartFragment -> {
                toolbarHome.isVisible = false
                toolbarCart.isVisible = true
                toolbarCart.title = "Cart"
            }
            is OrderListFragment -> {
                toolbarHome.isVisible = false
                toolbarCart.isVisible = true
                toolbarCart.title = "Order List"
            }
            is RecentlyViewedFragment -> {
                toolbarHome.isVisible = false
                toolbarCart.isVisible = true
                toolbarCart.title = "Recently viewed products"
            }
            is HomeFragment, is DetailFragment -> {
                toolbarHome.isVisible = true
                toolbarCart.isVisible = false
            }
        }
    }

    private fun replaceToCart() {
        supportFragmentManager.replaceWithPopBackstack(
            CartFragment::class.java,
            binding.fcvMain.id,
            CartFragment.TAG
        )
    }

    private fun replaceToOrderList() {
        supportFragmentManager.replace(
            OrderListFragment::class.java,
            binding.fcvMain.id,
            OrderListFragment.TAG
        )
    }

    private fun replaceToOrderDetail(deliveryTime: Long) {
        changeToolbar(
            supportFragmentManager.replace(
                OrderDetailFragment::class.java,
                binding.fcvMain.id,
                OrderDetailFragment.TAG,
                bundleOf(OrderDetailFragment.DETAIL_TIME to deliveryTime)
            )
        )
    }
}