package com.archestro.grocery.presentation.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.archestro.grocery.base.BaseViewModel
import com.archestro.grocery.data.source.remote.model.ErrorModel
import com.archestro.grocery.data.source.remote.model.response.product.Product
import com.archestro.grocery.domain.usecases.base.Outcome
import com.archestro.grocery.domain.usecases.base.UseCaseResponse
import com.archestro.grocery.domain.usecases.productDetailUseCase.GetProductDetailUseCase

class ProductDetailViewModel(
    private val productId:Int,
    private val productDetailUseCase: GetProductDetailUseCase
) : BaseViewModel() {

    init {
        getProductDetail()
    }

    private val productDetail= MutableLiveData<Product>()

    val productDetailLiveData:LiveData<Product>
        get() = productDetail

    private fun getProductDetail(){
        outcomeLiveData.value=Outcome.Start<Any>()
        productDetailUseCase.invoke(
            scope = scope,
            productId,
            onResult = object : UseCaseResponse<Product> {
                override fun onSuccess(result: List<Product>) {
                    outcomeLiveData.value = Outcome.End<Any>()
                    result.let {
                        productDetail?.value=result[0]
                    }
                }

                override fun onError(errorModel: ErrorModel?) {
                    outcomeLiveData.value = Outcome.Failure<Any>(errorModel)
                }
            })
    }
}