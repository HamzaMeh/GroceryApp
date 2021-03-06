package com.archestro.grocery.presentation.home


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.archestro.grocery.base.BaseViewModel
import com.archestro.grocery.data.source.remote.model.ErrorModel
import com.archestro.grocery.data.source.remote.model.response.category.Category
import com.archestro.grocery.data.source.remote.model.response.product.Product
import com.archestro.grocery.domain.usecases.allProductsUseCase.GetAllProductsUseCase
import com.archestro.grocery.domain.usecases.categoriesUseCase.GetCategoriesUseCase
import com.archestro.grocery.domain.usecases.base.Outcome
import com.archestro.grocery.domain.usecases.base.UseCaseResponse


class HomeViewModel(
   private var categoriesUseCase: GetCategoriesUseCase,
   private val productsUseCase: GetAllProductsUseCase
) : BaseViewModel() {

    init {
        getAllCategories()
        getAllProducts()
    }

    private val category= MutableLiveData<List<Category>>()
    private  val product= MutableLiveData<List<Product>>()

    val categoryLiveData:LiveData<List<Category>>
        get() = category

    val productLiveData:LiveData<List<Product>>
        get() = product



    private fun getAllCategories() {
        outcomeLiveData.value = Outcome.Start<Any>()
        categoriesUseCase.invoke(
            scope = scope,
            null,
            onResult = object : UseCaseResponse<Category> {
                override fun onSuccess(result: List<Category>) {
                    outcomeLiveData.value = Outcome.End<Any>()
                    category?.value=result

                }

                override fun onError(errorModel: ErrorModel?) {
                    outcomeLiveData.value = Outcome.Failure<Any>(errorModel)
                }
            })
    }

    private fun getAllProducts() {
        outcomeLiveData.value = Outcome.Start<Any>()
        productsUseCase.invoke(
            scope = scope,
            null,
            onResult = object : UseCaseResponse<Product> {
                override fun onSuccess(result: List<Product>) {
                    outcomeLiveData.value = Outcome.End<Any>()
                    product?.value=result
                }

                override fun onError(errorModel: ErrorModel?) {
                    outcomeLiveData.value = Outcome.Failure<Any>(errorModel)
                }
            }
        )

    }
}