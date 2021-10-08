package com.archestro.grocery.data.repository

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
            }
            downloadAllProducts.observeForever(){newFetchedProduct->
                newFetchedProduct.map { it->
                    persistFetchedProducts(it)
                }
            }
        }
    }


    override suspend fun getAllProducts():LiveData<List<Product>> {

        return withContext(Dispatchers.IO){
           fetchProducts()
            return@withContext productsDao.getAllProducts()
        }
    }

    override suspend fun getAllCategories(): LiveData<List<Category>> {


        return withContext(Dispatchers.IO) {
            fetchCategories()
            return@withContext categoriesDao.getAllCategories()
        }
    }

    override suspend fun getCategoryProducts(category:String): LiveData<List<Product>> {

        return withContext(Dispatchers.IO){
         //   initGrocery()
            return@withContext productsDao.getCategoryProducts(category)
        }
    }

    override suspend fun getProductDetail(productID:Int):LiveData<List<Product>>{
        val result=productsDao.getProductDetail(productID)
        return withContext(Dispatchers.IO){
            return@withContext productsDao.getProductDetail(productID)
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