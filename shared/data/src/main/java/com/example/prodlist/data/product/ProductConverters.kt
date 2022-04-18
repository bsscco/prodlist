package com.example.prodlist.data.product

import com.example.prodlist.domain.product.Product

internal fun DataProduct.toDomain(): Product {
    return Product(
        key = key,
        categoryKey = categoryKey,
        name = name,
        price = price,
        liked = liked ?: false,
    )
}