package com.archestro.grocery.module

import com.archestro.grocery.BuildConfig
import com.archestro.grocery.data.source.remote.ApiService
import com.archestro.grocery.data.source.remote.NetworkDataSource
import com.archestro.grocery.data.source.remote.NetworkDataSourceImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val NetworkModule= module {

    single { createService(get()) }

    single { createRetrofit(get(),BuildConfig.BASE_URL) }

    single { createOkHttpClient() }



}

fun createOkHttpClient():OkHttpClient{
    val interceptor=HttpLoggingInterceptor()
    if(BuildConfig.IS_DEBUG){
        interceptor.level=HttpLoggingInterceptor.Level.BODY
    }

    return OkHttpClient.Builder()
        .connectTimeout(60,TimeUnit.SECONDS)
        .readTimeout(60,TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()
}

fun createRetrofit(okHttpClient: OkHttpClient,url:String):Retrofit{

    return Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}

fun createService(retrofit: Retrofit):ApiService{
    return retrofit.create(ApiService::class.java)
}