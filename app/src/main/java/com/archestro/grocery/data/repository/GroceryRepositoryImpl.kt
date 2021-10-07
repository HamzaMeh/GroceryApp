package com.archestro.grocery.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.archestro.grocery.data.source.local.dao.CategoriesDao
import com.archestro.grocery.data.source.local.dao.ProductsDao
import com.archestro.grocery.data.source.remote.NetworkDataSource
import com.archestro.grocery.data.source.remote.model.response.category.Category
import com.archestro.grocery.data.source.remote.model.response.product.Product
import com.archestro.grocery.domain.repository.GroceryRepository
import kotlinx.coroutines.*
import java.time.ZonedDateTime


class GroceryRepositoryImpl(
    private val categoriesDao: CategoriesDao,
    private val productsDao: ProductsDao,
    private val networkDataSource: NetworkDataSource
) : GroceryRepository {

    private val categoryList= mutableListOf<Category>()
    init {

        networkDataSource.apply {

            downloadAllCategories.observeForever{newFetchedCategory->
                newFetchedCategory.map { it->
                    persistFetchedCategories(it)
                }
                /*if(categoryList.isEmpty()) {
                    categoryList.addAll(newFetchedCategory)
                    Log.e("Message Sequence","Added ")

                }*/
            }
            downloadAllProducts.observeForever(){newFetchedProduct->
                //persistFetchedProducts(newFetchedProduct)
            }
        }
    }


    override suspend fun getAllProducts():LiveData<List<Product>> {

        return withContext(Dispatchers.IO){
           fetchProducts()
            return@withContext networkDataSource.downloadAllProducts
        }
    }

    override suspend fun getAllCategories(): LiveData<List<Category>> {


        return withContext(Dispatchers.IO) {
            fetchCategories()
            return@withContext networkDataSource.downloadAllCategories
        }
    }

    override suspend fun getCategoryProducts(category:String): List<Product> {

        return withContext(Dispatchers.IO){
         //   initGrocery()
            return@withContext productsDao.getCategoryProducts(category)
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

    private suspend fun getCategory(): MutableList<Category> {

        if(categoryList.isEmpty())
        {
            networkDataSource.fetchAllCategories()
        }
        return categoryList
    }

    private suspend fun initGrocery()
    {
        val categories=categoriesDao.getAllCategories()
    //    if(categories.isEmpty()){
            fetchCategories()
          //  fetchProducts()
            return
      //  }

    }

    private suspend fun fetchCategories()
    {
        networkDataSource.fetchAllCategories()
    }

    private suspend fun fetchProducts()
    {
        networkDataSource.fetchAllProducts()
    }

    private fun isFetchNeeded(lastFetchedTime: ZonedDateTime):Boolean{
        val thirtyMinutesAgo= ZonedDateTime.now().minusMinutes(30)
        return lastFetchedTime.isBefore(thirtyMinutesAgo)
    }

}