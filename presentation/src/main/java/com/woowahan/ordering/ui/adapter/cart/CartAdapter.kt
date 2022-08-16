package com.woowahan.ordering.ui.adapter.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.ItemCartBinding
import com.woowahan.ordering.databinding.ItemCartHeaderBinding
import com.woowahan.ordering.databinding.ItemTotalPriceBinding
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.CartListItem
import com.woowahan.ordering.ui.adapter.cartListDiffUtil

class CartAdapter(
    private val selectAllClick: () -> Unit,
    private val checkItemClick: (Cart) -> Unit,
    private val minusItemClick: (Cart) -> Unit,
    private val plusItemClick: (Cart) -> Unit,
    private val deleteItemClick: (Cart) -> Unit,
    private val deleteAllClick: () -> Unit,
    private val orderClick: () -> Unit
) : ListAdapter<CartListItem, RecyclerView.ViewHolder>(cartListDiffUtil) {

    class CartHeaderViewHolder(private val binding: ItemCartHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            data: CartListItem.Header,
            selectAllClick: () -> Unit,
            deleteAllClick: () -> Unit
        ) = with(binding) {
            isSelectedAll = data.isSelectedAll
            btnDeselect.setOnClickListener { selectAllClick() }
            btnDelete.setOnClickListener { deleteAllClick() }
        }
    }

    class CartContentViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            data: CartListItem.Content,
            checkItemClick: (Cart) -> Unit,
            minusItemClick: (Cart) -> Unit,
            plusItemClick: (Cart) -> Unit,
            deleteItemClick: (Cart) -> Unit,
        ) = with(binding) {
            cart = data.cart

            with(data.cart) {
                ivCheckbox.setOnClickListener { checkItemClick(this) }
                btnMinus.setOnClickListener { minusItemClick(this) }
                btnPlus.setOnClickListener { plusItemClick(this) }
                ibtDelete.setOnClickListener { deleteItemClick(this) }
            }
        }
    }

    class CartTotalViewHolder(private val binding: ItemTotalPriceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CartListItem.Footer, orderClick: () -> Unit) {
            with(binding) {
                isCart = true
                sumOfPrice = data.sum
                deliveryFee = data.deliveryFee
                insufficientAmount = data.insufficientAmount
                totalPrice = data.sum + data.deliveryFee
                enableToOrder = data.enableToOrder

                btnOrder.setOnClickListener { orderClick() }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_cart -> CartContentViewHolder(
                ItemCartBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_cart_header -> CartHeaderViewHolder(
                ItemCartHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_total_price -> CartTotalViewHolder(
                ItemTotalPriceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CartHeaderViewHolder -> holder.bind(
                getItem(position) as CartListItem.Header,
                selectAllClick, deleteAllClick
            )
            is CartContentViewHolder -> holder.bind(
                getItem(position) as CartListItem.Content,
                checkItemClick, minusItemClick, plusItemClick, deleteItemClick
            )
            is CartTotalViewHolder -> holder.bind(
                getItem(position) as CartListItem.Footer,
                orderClick
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CartListItem.Header -> R.layout.item_cart_header
            is CartListItem.Content -> R.layout.item_cart
            is CartListItem.Footer -> R.layout.item_total_price
        }
    }
}