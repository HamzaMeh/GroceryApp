package com.archestro.grocery.presentation.categoryProducts


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.archestro.grocery.base.BaseViewModel
import com.archestro.grocery.data.source.remote.model.ErrorModel
import com.archestro.grocery.data.source.remote.model.response.category.Category
import com.archestro.grocery.data.source.remote.model.response.product.Product
import com.archestro.grocery.domain.usecases.base.Outcome
import com.archestro.grocery.domain.usecases.base.UseCaseResponse
import com.archestro.grocery.domain.usecases.categoryProductsUseCase.GetCategoryProductsUseCase

class CategoryViewModel(
    private var _category:String,
    private val productsUseCase: GetCategoryProductsUseCase
) : BaseViewModel() {

    init {
        getcategoryProducts()
    }

    private val categoryProducts= MutableLiveData<List<Product>>()

    val categoryProductData:LiveData<List<Product>>
        get() = categoryProducts

    private fun getcategoryProducts() {
        outcomeLiveData.value = Outcome.Start<Any>()
        productsUseCase.invoke(
            scope = scope,
            _category,
            onResult = object : UseCaseResponse<Product> {
                override fun onSuccess(result: List<Product>) {
                    outcomeLiveData.value = Outcome.End<Any>()
                    categoryProducts?.value=result

                }

                override fun onError(errorModel: ErrorModel?) {
                    outcomeLiveData.value = Outcome.Failure<Any>(errorModel)
                }
            })
    }

}