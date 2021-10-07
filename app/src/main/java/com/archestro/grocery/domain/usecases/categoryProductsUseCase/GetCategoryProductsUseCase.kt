package com.archestro.grocery.domain.usecases.categoryProductsUseCase

import androidx.lifecycle.LiveData
import com.archestro.grocery.data.source.remote.ApiErrorHandle
import com.archestro.grocery.data.source.remote.model.response.product.Product
import com.archestro.grocery.domain.repository.GroceryRepository
import com.archestro.grocery.domain.usecases.base.BaseUseCase

class GetCategoryProductsUseCase(
    private val repository: GroceryRepository,
    apiErrorHandle: ApiErrorHandle
) : BaseUseCase<Product>(apiErrorHandle){

    override suspend fun run(parm:Any?): LiveData<List<Product>> {
        return repository.getAllProducts()
    }
}