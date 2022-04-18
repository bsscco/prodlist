package com.example.prodlist.local.product

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "products")
internal data class ProductEntity(
    @PrimaryKey val key: String,
    val categoryKey: String,
    val name: String,
    val price: Int,
)