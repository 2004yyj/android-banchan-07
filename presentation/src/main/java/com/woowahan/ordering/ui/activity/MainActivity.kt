package com.woowahan.ordering.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.ActionCartBinding
import com.woowahan.ordering.databinding.ActionOrderBinding
import com.woowahan.ordering.databinding.ActivityMainBinding
import com.woowahan.ordering.ui.util.add

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cartBinding: ActionCartBinding
    private lateinit var orderBinding: ActionOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cartBinding = ActionCartBinding.inflate(layoutInflater)
        orderBinding = ActionOrderBinding.inflate(layoutInflater)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initFragment()
        initToolbar()
        initListener()
    }

    private fun initFragment() = with(binding) {
        val id = binding.fcvMain.id
        val fragmentManager = supportFragmentManager
    }

    private fun initToolbar() = with(binding) {
        toolbarHome.menu.findItem(R.id.item_cart).actionView = cartBinding.root
        toolbarHome.menu.findItem(R.id.item_order).actionView = orderBinding.root
    }

    private fun initListener() = with(binding) {
        cartBinding.ibtCart.setOnClickListener {
            // 장바구니
        }
        orderBinding.ibtOrder.setOnClickListener {
            // 주문내역
        }
    }
}