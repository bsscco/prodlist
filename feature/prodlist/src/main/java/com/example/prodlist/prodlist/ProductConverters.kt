package com.example.prodlist.prodlist

import com.example.prodlist.domain.product.Product
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal suspend fun List<Product>.toState(
    coroutineContext: CoroutineContext,
): List<ProductListContract.State.Product> {
    return withContext(coroutineContext) {
        this@toState
            .map { product ->
                ProductListContract.State.Product(
                    productKey = product.key,
                    name = product.name,
                    price = product.price,
                    liked = product.liked,
                )
            }
    }
}
