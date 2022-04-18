package com.example.prodlist.prodlist

import com.example.prodlist.domain.category.Category
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal suspend fun List<Category>.toState(
    coroutineContext: CoroutineContext,
    selectedKey: String,
): List<ProductListContract.State.Category> {
    return withContext(coroutineContext) {
        map { category ->
            ProductListContract.State.Category(
                categoryKey = category.key,
                name = category.name,
                selected = category.key == selectedKey,
            )
        }
    }
}