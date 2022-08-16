package com.woowahan.ordering.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Cart",
    foreignKeys = [ForeignKey(
        entity = OrderEntity::class,
        childColumns = arrayOf("orderId"),
        parentColumns = arrayOf("id")
    )],
    indices = [
        Index(value = ["detailHash"], unique = true)
    ]
)
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val title: String,
    val thumbnail: String,
    val price: Long,
    val count: Int,
    val detailHash: String,
    val isChecked: Boolean,
    val orderId: Int?
) {
}