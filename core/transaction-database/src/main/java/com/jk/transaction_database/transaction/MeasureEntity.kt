package com.jk.transaction_database.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("measure")
data class MeasureEntity(
    @PrimaryKey
    val id:String,
    @ColumnInfo(name="name")
    val name:String
) {
}