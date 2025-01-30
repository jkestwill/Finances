package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.jk.transaction_database.transaction.entity.LanguageEntity
import com.jk.transaction_database.transaction.entity.MeasureEntity

@Entity(tableName = "lang_measure_list",primaryKeys = ["measure_id","lang_id"], foreignKeys =  [
    ForeignKey(
        entity = MeasureEntity::class,
        childColumns  = ["measure_id"],
        parentColumns = ["id"],
    ),
    ForeignKey(
        entity = LanguageEntity::class,
        childColumns  = ["lang_id"],
        parentColumns = ["id"],
    )
])
data class LangMeasureListEntity(
    @ColumnInfo("measure_id")
    val measureId:String,
    @ColumnInfo("lang_id")
    val langId:String,
    @ColumnInfo("text")
    val text:String
) {
}