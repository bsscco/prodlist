package com.example.prodlist.data.product

data class DataProduct(
    val key: String,
    val categoryKey: String,
    val name: String,
    val price: Int,
    val liked: Boolean?,
)