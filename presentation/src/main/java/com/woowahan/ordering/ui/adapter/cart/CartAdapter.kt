package com.woowahan.ordering.ui.adapter.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.*
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.ui.adapter.cartListDiffUtil
import com.woowahan.ordering.ui.decorator.ItemSpacingDecoratorWithHeader
import com.woowahan.ordering.ui.fragment.cart.CartListItem
import com.woowahan.ordering.util.dp

class CartAdapter(
    private val selectAllClick: () -> Unit,
    private val checkItemClick: (Cart) -> Unit,
    private val minusItemClick: (Cart) -> Unit,
    private val plusItemClick: (Cart) -> Unit,
    private val deleteItemClick: (Cart) -> Unit,
    private val deleteAllClick: () -> Unit,
    private val orderClick: (String, Int) -> Unit,
    private val onDetailClick: (String, String) -> Unit,
    private val seeAllClick: () -> Unit
) : ListAdapter<CartListItem, RecyclerView.ViewHolder>(cartListDiffUtil) {

    class CartHeaderViewHolder(
        private val binding: ItemCartHeaderBinding,
        private val selectAllClick: () -> Unit,
        private val deleteAllClick: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CartListItem.Header) = with(binding) {
            isSelectedAll = data.isSelectedAll
            btnDeselect.setOnClickListener { selectAllClick() }
            btnDelete.setOnClickListener { deleteAllClick() }
        }
    }

    class CartContentViewHolder(
        private val binding: ItemCartBinding,
        private val checkItemClick: (Cart) -> Unit,
        private val minusItemClick: (Cart) -> Unit,
        private val plusItemClick: (Cart) -> Unit,
        private val deleteItemClick: (Cart) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CartListItem.Content) = with(binding) {
            cart = data.cart

            with(data.cart) {
                ivCheckbox.setOnClickListener { checkItemClick(this) }
                btnMinus.setOnClickListener { minusItemClick(this) }
                btnPlus.setOnClickListener { plusItemClick(this) }
                ibtDelete.setOnClickListener { deleteItemClick(this) }
            }
        }
    }

    class CartTotalViewHolder(
        private val binding: ItemTotalPriceBinding,
        private val orderClick: (String, Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CartListItem.Footer) = with(binding) {
            sumOfPrice = data.sum
            deliveryFee = data.deliveryFee
            insufficientAmount = data.insufficientAmount
            totalPrice = data.sum + data.deliveryFee
            enableToOrder = data.enableToOrder

            btnOrder.setOnClickListener { orderClick(data.title, data.count) }
        }
    }

    class EmptyViewHolder(binding: ItemEmptyBinding) : RecyclerView.ViewHolder(binding.root)

    class CartRecentlyItemViewHolder(
        private val binding: ItemCartRecentlyBinding,
        private val onDetailClick: (String, String) -> Unit,
        private val seeAllClick: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private val adapter = RecentlyAdapter(RecentlyAdapter.RecentlyItemViewType.HorizontalItem)
        private val decoration = ItemSpacingDecoratorWithHeader(
            spacing = 8.dp,
            spaceAdapters = listOf(adapter)
        )

        fun bind(data: CartListItem.CartHistory) = with(binding) {
            adapter.submitList(data.historyList)
            adapter.setOnClick(onDetailClick)

            tvSeeAll.setOnClickListener { seeAllClick() }
            rvRecently.adapter = adapter
            if (rvRecently.itemDecorationCount == 0) {
                rvRecently.addItemDecoration(decoration)
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
                ),
                checkItemClick, minusItemClick, plusItemClick, deleteItemClick
            )
            R.layout.item_cart_header -> CartHeaderViewHolder(
                ItemCartHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                selectAllClick, deleteAllClick
            )
            R.layout.item_total_price -> CartTotalViewHolder(
                ItemTotalPriceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                orderClick
            )
            R.layout.item_empty -> EmptyViewHolder(
                ItemEmptyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_recently_horizontal -> CartRecentlyItemViewHolder(
                ItemCartRecentlyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onDetailClick, seeAllClick
            )
            else -> throw IllegalArgumentException()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CartHeaderViewHolder -> holder.bind(getItem(position) as CartListItem.Header)
            is CartContentViewHolder -> holder.bind(getItem(position) as CartListItem.Content)
            is CartTotalViewHolder -> holder.bind(getItem(position) as CartListItem.Footer)
            is CartRecentlyItemViewHolder -> holder.bind(getItem(position) as CartListItem.CartHistory)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }

        payloads.forEach {
            if (it is Boolean) {
                onBindViewHolder(holder, position)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CartListItem.Header -> R.layout.item_cart_header
            is CartListItem.Content -> R.layout.item_cart
            is CartListItem.Footer -> R.layout.item_total_price
            is CartListItem.Empty -> R.layout.item_empty
            is CartListItem.CartHistory -> R.layout.item_recently_horizontal
        }
    }
}