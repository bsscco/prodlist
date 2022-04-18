package com.example.prodlist.local.product

import androidx.room.*
import com.example.prodlist.local.product.like.ProdLikeEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface RoomProductDao {

    @Query("SELECT * FROM products WHERE categoryKey = :categoryKey")
    fun getFilteredProductsByCategory(categoryKey: String): Flow<List<ProductAndLikeEntity>>

    @Query("SELECT * FROM products JOIN prodLikes ON products.`key` = prodLikes.productKey WHERE liked = 1")
    fun getFavoriteProducts(): Flow<List<ProductAndLikeEntity>>

    @Query("SELECT * FROM products JOIN prodLikes ON products.`key` = prodLikes.productKey WHERE liked = 1 AND name LIKE '%' || :keyword || '%' ")
    fun getSearchedFavoriteProducts(keyword: String): Flow<List<ProductAndLikeEntity>>

    @Query("SELECT COUNT(*) FROM products LIMIT 1")
    fun hasAnyProduct(): Flow<Boolean>

    @Query("SELECT COUNT(*) FROM products WHERE `key` = :productKey")
    suspend fun hasProduct(productKey: String): Boolean

    @Query("SELECT * FROM products WHERE `key` = :productKey")
    fun getProduct(productKey: String): Flow<ProductAndLikeEntity?>

    @Query("DELETE FROM products")
    suspend fun clearProducts()

    @Transaction
    suspend fun setProducts(products: List<ProductEntity>) {
        clearProducts()
        insertProducts(products)
    }

    @Insert
    suspend fun insertProducts(products: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setLike(like: ProdLikeEntity)
}