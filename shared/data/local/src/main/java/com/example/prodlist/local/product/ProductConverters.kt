package com.example.prodlist.local.product

import com.example.prodlist.data.product.DataProduct

internal fun DataProduct.toEntity(): ProductEntity {
    return ProductEntity(
        key = key,
        categoryKey = categoryKey,
        name = name,
        price = price,
    )
}

internal fun ProductAndLikeEntity.toData(): DataProduct {
    return DataProduct(
        key = product.key,
        categoryKey = product.categoryKey,
        name = product.name,
        price = product.price,
        liked = like?.liked,
    )
}