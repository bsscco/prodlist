package com.example.prodlist.data.category

import com.example.prodlist.domain.category.Category

internal fun List<DataCategory>.toDomain(): List<Category> {
    return map { category ->
        Category(
            key = category.key,
            name = category.name,
        )
    }
}