package com.archestro.grocery.module

import com.archestro.grocery.data.repository.GroceryRepositoryImpl
import com.archestro.grocery.data.source.local.dao.CategoriesDao
import com.archestro.grocery.data.source.local.dao.ProductsDao
import com.archestro.grocery.data.source.local.db.GroceryDatabase
import com.archestro.grocery.data.source.remote.ApiErrorHandle
import com.archestro.grocery.data.source.remote.ApiService
import com.archestro.grocery.data.source.remote.NetworkDataSource
import com.archestro.grocery.data.source.remote.NetworkDataSourceImpl
import com.archestro.grocery.domain.repository.GroceryRepository
import com.archestro.grocery.domain.usecases.allProductsUseCase.GetAllProductsUseCase
import com.archestro.grocery.domain.usecases.categoriesUseCase.GetCategoriesUseCase
import com.archestro.grocery.domain.usecases.cateogryProductsUseCase.GetCategoryProductsUseCase
import com.archestro.grocery.presentation.home.HomeViewModel
import com.archestro.grocery.util.AppUtils
import com.archestro.grocery.util.AppUtils.context
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule= module {

    single { createNetworkDataSource(get()) }

    single { createApiErrorHandle() }

    single { GroceryDatabase.getInstance(context = AppUtils.context) }

    single { get<GroceryDatabase>().productsDao() }

    single { get<GroceryDatabase>().categoriesDao() }

    single { createRepository(get(),get(),get()) }

    single { createAllProductsUseCase(get(), get()) }

    single { createCategoriesUseCase(get(), get()) }

    single { createCategoryProductsUseCase(get(),get())}

    viewModel {
        HomeViewModel(get())
    }
}
fun createRepository(
categoriesDao: CategoriesDao,
productsDao: ProductsDao,
networkDataSource:NetworkDataSource
):GroceryRepository{
    return GroceryRepositoryImpl(categoriesDao,productsDao,networkDataSource)
}

fun createNetworkDataSource(apiService: ApiService):NetworkDataSource{
    return NetworkDataSourceImpl(apiService)
}

fun createCategoryProductsUseCase(
    groceryRepository: GroceryRepository,
    apiErrorHandle: ApiErrorHandle
): GetCategoryProductsUseCase {
    return GetCategoryProductsUseCase(groceryRepository,apiErrorHandle)
}

fun createCategoriesUseCase(
    groceryRepository: GroceryRepository,
    apiErrorHandle: ApiErrorHandle
):GetCategoriesUseCase{
    return GetCategoriesUseCase(groceryRepository,apiErrorHandle)
}

fun createAllProductsUseCase(
    groceryRepository: GroceryRepository,
    apiErrorHandle: ApiErrorHandle
):GetAllProductsUseCase{
    return GetAllProductsUseCase(groceryRepository,apiErrorHandle)
}

fun createApiErrorHandle():ApiErrorHandle{
    return ApiErrorHandle()
}