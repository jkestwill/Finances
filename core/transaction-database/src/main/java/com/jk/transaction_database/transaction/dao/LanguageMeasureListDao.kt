package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LanguageMeasureListDao {
//    @Query(
//        "SELECT * FROM lang_measure_list " +
//                "INNER JOIN measure ON measure.id == measure_id " +
//                "INNER JOIN language ON language.id == lang_id"
//    )
//   suspend fun getMeasureRelation():MeasureRelation

@Query("INSERT INTO lang_measure_list VALUES(:measureId,:languageId,:text)")
   suspend fun insert(measureId:String,languageId:String,text:String)

}