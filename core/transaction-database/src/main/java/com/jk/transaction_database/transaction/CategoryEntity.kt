package com.jk.transaction_database.transaction

import androidx.room.ColumnInfo
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