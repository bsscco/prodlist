package com.example.prodlist.local.category

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "categories")
internal data class CategoryEntity(
    @PrimaryKey val key: String,
    val name: String,
)