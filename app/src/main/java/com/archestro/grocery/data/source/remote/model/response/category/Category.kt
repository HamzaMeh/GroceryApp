package com.archestro.grocery.data.source.remote.model.response.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categories")
data class Category(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String,
    val name: String
)