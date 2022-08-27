package com.woowahan.ordering.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.woowahan.ordering.data.entity.CartEntity
import com.woowahan.ordering.data.entity.OrderEntity
import com.woowahan.ordering.data.entity.SimpleOrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert
    fun insertOrder(order: OrderEntity): Long

    @Query("UPDATE Orders SET isDelivered = :isDelivered WHERE deliveryTime = :deliveryTime")
    fun updateOrder(deliveryTime: Long, isDelivered: Boolean)

    @Query(
        "SELECT c.title, c.thumbnail, o.deliveryTime, o.isDelivered, total(c.price) as totalPrice, count(o.deliveryTime) as productCount " +
                "FROM Orders as o LEFT JOIN Cart as c ON o.id = c.orderId " +
                "GROUP BY o.deliveryTime ORDER BY o.deliveryTime DESC"
    )
    fun getSimpleOrder(): PagingSource<Int, SimpleOrderEntity>

    @Query(
        "SELECT c.* FROM Orders as o LEFT JOIN Cart as c ON o.id = c.orderId " +
                "WHERE o.deliveryTime = :deliveryTime ORDER BY o.deliveryTime DESC"
    )
    fun getOrderedCartByDeliveryTime(deliveryTime: Long): List<CartEntity>

    @Query(
        "SELECT EXISTS (SELECT c.* FROM Orders as o LEFT JOIN Cart as c ON o.id = c.orderId " +
                "WHERE o.isDelivered = 0)"
    )
    fun isExistNotDeliveredOrder(): Flow<Boolean>
}