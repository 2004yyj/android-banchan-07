package com.woowahan.ordering.data.local.dao

import androidx.room.*
import com.woowahan.ordering.data.entity.CartEntity

@Dao
interface CartDao {
    @Insert
    fun insertCart(cart: CartEntity)

    @Update
    fun updateCart(cart: CartEntity)

    @Delete
    fun deleteCart(cart: CartEntity)

    @Query("SELECT * FROM Cart WHERE orderId is null")
    fun getCart(): List<CartEntity>

    @Query("SELECT EXISTS (SELECT * FROM Cart WHERE orderId is null AND detailHash = :detailHash)")
    fun isExistNotOrderedCart(detailHash: String): Boolean
}