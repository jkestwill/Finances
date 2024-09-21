package com.jk.transaction_database.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class AddressEntity(
    @PrimaryKey
    val id:String,
    @ColumnInfo(name="long")
    val long:Short,
    @ColumnInfo(name="lat")
    val lat:Short,
    @ColumnInfo(name="address")
    val address:String?
)