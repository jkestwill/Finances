package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jk.transaction_database.transaction.relations.MeasureRelation

@Dao
interface LanguageMeasureListDao {
    @Query(
        "SELECT * FROM lang_measure_list " +
                "INNER JOIN measure ON measure.id == measure_id " +
                "INNER JOIN language ON language.id == language.id"
    )
   suspend fun getMeasureRelation():MeasureRelation

@Query("INSERT INTO lang_measure_list VALUES(:measureId,:languageId)")
   suspend fun insert(measureId:String,languageId:String)

}