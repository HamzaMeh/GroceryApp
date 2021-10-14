package com.archestro.grocery.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.archestro.grocery.data.source.local.dao.CategoriesDao
import com.archestro.grocery.data.source.local.dao.ProductsDao
import com.archestro.grocery.data.source.remote.NetworkDataSource
import com.archestro.grocery.data.source.remote.model.response.category.Category
import com.archestro.grocery.data.source.remote.model.response.product.Product
import com.archestro.grocery.domain.repository.GroceryRepository
import kotlinx.coroutines.*
import java.time.ZonedDateTime
import kotlin.math.log


class GroceryRepositoryImpl(
    private val categoriesDao: CategoriesDao,
    private val productsDao: ProductsDao,
    private val networkDataSource: NetworkDataSource
) : GroceryRepository {

    init {

        networkDataSource.apply {

            downloadAllCategories.observeForever{newFetchedCategory->
                newFetchedCategory.map {
                    persistFetchedCategories(it)
                }
            }
            downloadAllProducts.observeForever(){newFetchedProduct->
                newFetchedProduct.map {
                    persistFetchedProducts(it)
                }
            }
        }
    }


    override suspend fun getAllProducts():List<Product> {
        return withContext(Dispatchers.IO){
            val data=productsDao.getAllProducts()
            if(data.isEmpty())
            {
                networkDataSource.fetchAllProducts()
                return@withContext productsDao.getAllProducts()
            }
            else{
                return@withContext data
            }
        }
    }

    override suspend fun getAllCategories(): List<Category> {
        return withContext(Dispatchers.IO){
            val data=categoriesDao.getAllCategories()
            if(data.isEmpty())
            {
                networkDataSource.fetchAllCategories()
                return@withContext categoriesDao.getAllCategories()
            }
            else{
                return@withContext data
            }


        }
    }


    override suspend fun getCategoryProducts(category:String): List<Product> {
        return withContext(Dispatchers.IO){
            return@withContext productsDao.getCategoryProducts(category)
        }
    }

    override suspend fun getProductDetail(productID:Int):List<Product>{

        return withContext(Dispatchers.IO){
            val prod=productsDao.getProductDetail(productID)
            return@withContext listOf(prod)
        }
    }


    private fun persistFetchedCategories(fetchedCategories:Category)
    {

        GlobalScope.launch(Dispatchers.IO) {
            categoriesDao.upsert(fetchedCategories)
        }
    }

    private fun persistFetchedProducts(fetchedProducts:Product)
    {

        GlobalScope.launch(Dispatchers.IO) {
            productsDao.upsert(fetchedProducts)
        }
    }



}