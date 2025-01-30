package com.jk.transaction_database.transaction.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("specifications",foreignKeys = [
    ForeignKey(
        entity = MeasureEntity::class,
        childColumns  = ["measure_id"],
        parentColumns = ["id"],
        onDelete = ForeignKey.CASCADE
    )
])
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