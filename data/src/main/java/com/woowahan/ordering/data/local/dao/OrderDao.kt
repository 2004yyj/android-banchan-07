package com.woowahan.ordering.data.local.dao

import androidx.room.*
import com.woowahan.ordering.data.entity.CartEntity
import com.woowahan.ordering.data.entity.OrderEntity
import com.woowahan.ordering.data.entity.SimpleOrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert
    fun insertOrder(order: OrderEntity): Long

    @Query(
        "SELECT c.title, c.thumbnail, o.deliveryTime, total(c.price) as totalPrice, count(o.deliveryTime) as productCount " +
                "FROM Orders as o LEFT JOIN Cart as c ON o.id = c.orderId " +
                "GROUP BY o.deliveryTime ORDER BY o.deliveryTime DESC"
    )
    fun getSimpleOrder(): Flow<List<SimpleOrderEntity>>

    @Query(
        "SELECT c.* FROM Orders as o LEFT JOIN Cart as c ON o.id = c.orderId " +
                "WHERE o.deliveryTime = :deliveryTime ORDER BY o.deliveryTime DESC"
    )
    fun getOrderedCartByDeliveryTime(deliveryTime: Long): Flow<List<CartEntity>>
}