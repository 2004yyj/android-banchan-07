package com.woowahan.ordering.data.local.dao

import androidx.room.*
import com.woowahan.ordering.data.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert
    fun insertCart(cart: CartEntity)

    @Update
    fun updateCart(cart: CartEntity)

    @Delete
    fun deleteCart(cart: CartEntity)

    @Query("SELECT * FROM Cart WHERE orderId is null")
    fun getCart(): Flow<List<CartEntity>>

    @Query("SELECT EXISTS (SELECT * FROM Cart WHERE orderId is null AND detailHash = :detailHash)")
    fun isExistNotOrderedCart(detailHash: String): Boolean

    @Query("UPDATE Cart SET isChecked = :option WHERE orderId is null")
    fun selectAllCartItem(option: Boolean)
}