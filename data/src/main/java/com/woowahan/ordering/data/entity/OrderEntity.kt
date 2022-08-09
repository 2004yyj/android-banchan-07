package com.woowahan.ordering.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Order")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val deliveryTime: Long
)