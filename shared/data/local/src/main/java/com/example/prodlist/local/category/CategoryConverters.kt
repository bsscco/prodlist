package com.example.prodlist.local.category

import com.example.prodlist.data.category.DataCategory

internal fun DataCategory.toEntity(): CategoryEntity {
    return CategoryEntity(
        key = key,
        name = name,
    )
}

internal fun CategoryEntity.toData(): DataCategory {
    return DataCategory(
        key = key,
        name = name,
    )
}