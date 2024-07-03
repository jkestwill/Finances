package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.jk.transaction_database.transaction.LanguageEntity
import com.jk.transaction_database.transaction.MeasureEntity

data class MeasureRelation(
    @Embedded
    val measureEntity: MeasureEntity,
    @Relation(parentColumn = "lang_id", entityColumn = "id")
    val lang: LanguageEntity
)
