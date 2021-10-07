package com.archestro.grocery.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.archestro.grocery.data.source.remote.model.response.category.Category
import com.archestro.grocery.data.source.remote.model.response.product.Product
import java.lang.Exception


class NetworkDataSourceImpl(private val apiService: ApiService) : NetworkDataSource {

    private val _downloadedAllCategories= MutableLiveData<List<Category>>()
    private val _downloadedAllProducts= MutableLiveData<Product> ()

    override val downloadAllCategories: LiveData<List<Category>>
        get() = _downloadedAllCategories
    override val downloadAllProducts: LiveData<Product>
        get() = _downloadedAllProducts

    override suspend fun fetchAllCategories() {
        try {
            val fetchedCategories=apiService
                .getCategories()
            _downloadedAllCategories.postValue(fetchedCategories)

        }catch (e: Exception)
        {
            Log.e("Exception in API",e.localizedMessage)
            e.printStackTrace()
        }
    }


    override suspend fun fetchAllProducts() {
        try {
            val fetchedProducts=apiService
                .getProducts()
            fetchedProducts.map { product->
                _downloadedAllProducts.postValue(product)
            }
        }catch (e: Exception)
        {
            e.printStackTrace()
        }
    }


}