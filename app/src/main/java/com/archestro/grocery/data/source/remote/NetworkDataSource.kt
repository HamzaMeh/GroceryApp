package com.archestro.grocery.data.source.remote

import androidx.lifecycle.LiveData
import com.archestro.grocery.data.source.remote.model.response.category.Category
import com.archestro.grocery.data.source.remote.model.response.product.Product

interface NetworkDataSource {

    val downloadAllCategories: LiveData<List<Category>>
    val downloadAllProducts: LiveData<List<Product>>

    suspend fun fetchAllCategories()

    suspend fun fetchAllProducts()


}