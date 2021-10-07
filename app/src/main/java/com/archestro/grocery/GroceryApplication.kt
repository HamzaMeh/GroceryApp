package com.archestro.grocery

import android.app.Application
import android.content.Context
import com.archestro.grocery.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GroceryApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GroceryApplication)
            modules(listOf(NetworkModule, appModule))
        }
    }

    init {
        instance=this
    }
    companion object{

        private var instance:GroceryApplication?=null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}