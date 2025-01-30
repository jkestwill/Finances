package com.jk.transaction_database.transaction.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "category"
)
data class CategoryEntity(
    @PrimaryKey
    val id:String,
    val name:String,

    val color:String
) {
}