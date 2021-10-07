package com.archestro.grocery.data.source.remote

import com.archestro.grocery.data.source.remote.model.response.category.Category
import com.archestro.grocery.data.source.remote.model.response.product.Product
import retrofit2.http.GET

interface ApiService {


    @GET("products")
    suspend fun getProducts(): List<Product>


    @GET("categories")
    suspend fun getCategories():List<Category>

}