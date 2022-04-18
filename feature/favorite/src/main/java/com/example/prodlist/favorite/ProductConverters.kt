package com.example.prodlist.favorite

import com.example.prodlist.domain.product.Product
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal suspend fun List<Product>.toState(
    coroutineContext: CoroutineContext,
): List<FavoriteListContract.State.Product> {
    return withContext(coroutineContext) {
        map { product ->
            FavoriteListContract.State.Product(
                productKey = product.key,
                name = product.name,
                price = product.price,
                liked = product.liked,
            )
        }
    }
}