package com.example.prodlist.domain.product

data class Product(
    val key: String,
    val categoryKey: String,
    val name: String,
    val price: Int,
    val liked: Boolean,
)