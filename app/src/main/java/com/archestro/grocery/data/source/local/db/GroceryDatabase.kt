package com.archestro.grocery.data.source.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.archestro.grocery.data.source.local.dao.CategoriesDao
import com.archestro.grocery.data.source.local.dao.ProductsDao
import com.archestro.grocery.data.source.remote.model.response.category.Category
import com.archestro.grocery.data.source.remote.model.response.product.Product


@Database(
    entities = [Category::class,Product::class],
    version = 1
)
abstract class GroceryDatabase: RoomDatabase() {

    abstract fun productsDao(): ProductsDao

    abstract fun categoriesDao(): CategoriesDao

    companion object{
        @Volatile
        private var INSTANCE: GroceryDatabase? = null

        fun getInstance(context: Context): GroceryDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GroceryDatabase::class.java,
                        "groceryDatabase"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        /*
        @Volatile
        private var instance:GroceryDatabase?=null

        //we need a lock so that no more than 1 instance can access it at one time
        private val LOCK=Any()

        operator fun invoke(context: Context)=instance?: synchronized(LOCK){
            instance?: buildDatabase(context).also{ instance=it}
        }

        private fun buildDatabase(context: Context)=
            Room.databaseBuilder(context.applicationContext,
                GroceryDatabase::class.java,"Groceries.db")
                .build()*/
    }

}