package com.archestro.grocery.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.archestro.grocery.data.source.remote.model.response.product.Product

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(product: Product)

    @Query("select * from products")
    fun getAllProducts(): List<Product>

    @Query("select * from products where category= :categoryToGet ")
    fun getCategoryProducts(categoryToGet:String): List<Product>


}