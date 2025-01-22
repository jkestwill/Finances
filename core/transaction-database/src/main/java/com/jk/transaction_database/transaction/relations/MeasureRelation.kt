package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jk.transaction_database.transaction.LanguageEntity
import com.jk.transaction_database.transaction.MeasureEntity
import com.jk.transaction_database.transaction.list.LangMeasureListEntity

data class MeasureRelation(

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = MeasureEntity::class ,
        associateBy = Junction(LangMeasureListEntity::class, parentColumn = "measure_id","lang_id")
    )
    val measureEntity: List<MeasureEntity>,
    @Embedded
    val lang: LanguageEntity
)
