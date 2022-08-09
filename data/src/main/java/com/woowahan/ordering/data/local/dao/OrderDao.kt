package com.woowahan.ordering.data.local.dao

import androidx.room.*
import com.woowahan.ordering.data.entity.CartEntity
import com.woowahan.ordering.data.entity.OrderEntity
import com.woowahan.ordering.data.entity.SimpleOrderEntity

@Dao
interface OrderDao {
    @Insert
    fun insertOrder(order: OrderEntity)

    @Query(
        "SELECT c.name, c.thumbnail, o.deliveryTime, total(c.price) as totalPrice, count(o.deliveryTime) as productCount " +
                "FROM Orders as o LEFT JOIN Cart as c ON o.id = c.orderId " +
                "GROUP BY o.deliveryTime ORDER BY o.deliveryTime DESC"
    )
    fun getSimpleOrder(): List<SimpleOrderEntity>

    @Query(
        "SELECT c.* FROM Orders as o LEFT JOIN Cart as c ON o.id = c.orderId " +
                "WHERE o.deliveryTime = :deliveryTime ORDER BY o.deliveryTime DESC"
    )
    fun getOrderedCartByDeliveryTime(deliveryTime: Long): List<CartEntity>
}