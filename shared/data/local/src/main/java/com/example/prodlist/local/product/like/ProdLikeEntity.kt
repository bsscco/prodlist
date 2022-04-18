package com.example.prodlist.local.product.like

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "prodLikes")
internal data class ProdLikeEntity(
    @PrimaryKey val productKey: String,
    val liked: Boolean,
)