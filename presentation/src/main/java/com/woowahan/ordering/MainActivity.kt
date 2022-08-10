package com.woowahan.ordering

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.woowahan.ordering.databinding.ActionCartBinding
import com.woowahan.ordering.databinding.ActionOrderBinding
import com.woowahan.ordering.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cartBinding: ActionCartBinding
    private lateinit var orderBinding: ActionOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cartBinding = ActionCartBinding.inflate(layoutInflater)
        orderBinding = ActionOrderBinding.inflate(layoutInflater)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initToolbar()
        initListener()
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