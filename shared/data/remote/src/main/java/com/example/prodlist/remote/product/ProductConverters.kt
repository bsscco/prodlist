package com.example.prodlist.remote.product

import com.example.prodlist.data.category.DataCategory
import com.example.prodlist.data.product.CategoriesAndProducts
import com.example.prodlist.data.product.DataProduct

internal fun GetProductionsResponse.toData(): CategoriesAndProducts {
    return CategoriesAndProducts(
        categories = categories.map { category ->
            DataCategory(
                key = category.key,
                name = category.name,
            )
        },
        products = products.map { product ->
            DataProduct(
                key = product.key,
                categoryKey = product.categoryKey,
                name = product.name,
                price = product.price,
                liked = null,
            )
        },
    )
}