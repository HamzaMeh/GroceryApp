package com.archestro.grocery.data.source.remote.model.response.product

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Products")
data class Product(
    val category: String,
    val description: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String,
    val price: Double,
    @Embedded(prefix="rating_")
    val rating: Rating,
    val title: String
)