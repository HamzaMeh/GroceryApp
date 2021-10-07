package com.archestro.grocery.presentation.home


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.archestro.grocery.base.BaseViewModel
import com.archestro.grocery.data.source.remote.model.ErrorModel
import com.archestro.grocery.data.source.remote.model.response.category.Category
import com.archestro.grocery.domain.usecases.categoriesUseCase.GetCategoriesUseCase
import com.archestro.grocery.domain.usecases.base.Outcome
import com.archestro.grocery.domain.usecases.base.UseCaseResponse


class HomeViewModel(
   private var categoriesUseCase: GetCategoriesUseCase
  // private val productsUseCase: GetAllProductsUseCase
) : BaseViewModel() {

    init {
        getAllCategories()
      //  getAllProducts()
    }

    private var category: MutableLiveData<List<Category>>? = null
   // private var product: MutableLiveData<List<Product>>? = null

    fun categoryLiveData() = category
  //  fun productLiveData() = product


    private fun getAllCategories() {
        outcomeLiveData.value = Outcome.Start<Any>()
        categoriesUseCase.invoke(
            scope = scope,
            null,
            onResult = object : UseCaseResponse<Category> {
                override fun onSuccess(result: List<Category>) {
                    outcomeLiveData.value = Outcome.End<Any>()
                    result.let {
                        category!!.value = result
                    }
                }

                override fun onError(errorModel: ErrorModel?) {
                    Log.e("Error",errorModel.toString())
                    outcomeLiveData.value = Outcome.Failure<Any>(errorModel)
                }
            })
    }

 /*   private fun getAllProducts() {
        outcomeLiveData.value = Outcome.Start<Any>()
        productsUseCase.invoke(
            scope = scope,
            null,
            onResult = object : UseCaseResponse<Product> {
                override fun onSuccess(result: List<Product>) {
                    outcomeLiveData.value = Outcome.End<Any>()
                    result.let {
                        product?.value = result
                    }
                }

                override fun onError(errorModel: ErrorModel?) {
                    outcomeLiveData.value = Outcome.Failure<Any>(errorModel)
                }
            }
        )

    }*/
}