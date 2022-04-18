package com.example.prodlist.remote.product

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class GetProductionsResponse(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String?,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("productions")
    val products: List<Production>,
) {
    @Keep
    data class Category(
        @SerializedName("key")
        val key: String,
        @SerializedName("name")
        val name: String,
    )

    @Keep
    data class Production(
        @SerializedName("key")
        val key: String,
        @SerializedName("categoryKey")
        val categoryKey: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("price")
        val price: Int,
    )
}