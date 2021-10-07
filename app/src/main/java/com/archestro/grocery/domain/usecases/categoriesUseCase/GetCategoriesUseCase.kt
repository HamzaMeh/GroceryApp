package com.archestro.grocery.domain.usecases.categoriesUseCase

import androidx.lifecycle.LiveData
import com.archestro.grocery.data.source.remote.ApiErrorHandle
import com.archestro.grocery.data.source.remote.model.response.category.Category
import com.archestro.grocery.domain.repository.GroceryRepository
import com.archestro.grocery.domain.usecases.base.BaseUseCase

class GetCategoriesUseCase(
    private val repository: GroceryRepository,
    apiErrorHandle: ApiErrorHandle
) : BaseUseCase<Category>(apiErrorHandle){

    override suspend fun run(parm:Any?): LiveData<List<Category>> {
        return repository.getAllCategories()
    }
}