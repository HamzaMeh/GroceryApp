package com.archestro.grocery.domain.repository

import androidx.lifecycle.LiveData
import com.archestro.grocery.data.source.remote.model.response.category.Category
import com.archestro.grocery.data.source.remote.model.response.product.Product


interface GroceryRepository {

    suspend fun getAllProducts(): List<Product>

    suspend fun getAllCategories(): List<Category>

    suspend fun getCategoryProducts(category:String): List<Product>

    suspend fun getProductDetail(productID:Int):List<Product>
}