package com.example.prodlist.local.product

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Relation
import com.example.prodlist.local.product.like.ProdLikeEntity

@Keep
internal data class ProductAndLikeEntity(
    @Embedded val product: ProductEntity,
    @Relation(
        parentColumn = "key",
        entityColumn = "productKey",
    )
    val like: ProdLikeEntity?,
)