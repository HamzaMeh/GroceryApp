package com.archestro.grocery.domain.usecases.productDetailUseCase

import androidx.lifecycle.LiveData
import com.archestro.grocery.data.source.remote.ApiErrorHandle
import com.archestro.grocery.data.source.remote.model.response.product.Product
import com.archestro.grocery.domain.repository.GroceryRepository
import com.archestro.grocery.domain.usecases.base.BaseUseCase

class GetProductDetailUseCase(
    private val repository: GroceryRepository,
     apiErrorHandle: ApiErrorHandle
):BaseUseCase<Product>(apiErrorHandle) {
    override suspend fun run(paramter: Any?): LiveData<List<Product>> {
        return repository.getProductDetail(paramter as Int)
    }

}