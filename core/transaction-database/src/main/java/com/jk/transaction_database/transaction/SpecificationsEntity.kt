package com.jk.transaction_database.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("specifications")
data class SpecificationsEntity(
    @PrimaryKey
    val id:String,
    @ColumnInfo("text")
    val text:String,
    @ColumnInfo("amount")
    val amount:Float,
    @ColumnInfo("measure_id")
    val measureId:String
) {
}