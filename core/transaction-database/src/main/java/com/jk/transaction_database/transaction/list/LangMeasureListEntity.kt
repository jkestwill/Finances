package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "lang_measure_list", primaryKeys = ["measure_id","lang_id"])
data class LangMeasureListEntity(
    @ColumnInfo("measure_id")
    val measureId:String,
    @ColumnInfo("lang_id")
    val langId:String
) {
}