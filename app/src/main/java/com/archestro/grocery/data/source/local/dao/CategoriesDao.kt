package com.archestro.grocery.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.archestro.grocery.data.source.remote.model.response.category.Category

@Dao
interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(category:Category)

    @Query("select * from categories")
    fun getAllCategories(): List<Category>

}