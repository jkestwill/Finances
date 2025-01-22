package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jk.transaction_database.transaction.MeasureEntity
import com.jk.transaction_database.transaction.SpecificationsEntity
import com.jk.transaction_database.transaction.list.LangMeasureListEntity

data class SpecificationRelation(
    @Embedded
    val specificationEntity: SpecificationsEntity,
    @Relation(
        parentColumn = "measure_id",
        entityColumn = "id",
        entity = MeasureEntity::class,
        associateBy = Junction(LangMeasureListEntity::class, parentColumn = "measure_id","measure_id")
    )
    val measure: MeasureRelation

) {
}