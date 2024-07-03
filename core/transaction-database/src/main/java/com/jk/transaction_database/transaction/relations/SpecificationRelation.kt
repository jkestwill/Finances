package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.jk.transaction_database.transaction.MeasureEntity
import com.jk.transaction_database.transaction.SpecificationsEntity

data class SpecificationRelation(
    @Embedded
    val id: SpecificationsEntity,
    @Relation(parentColumn = "measure_id", entityColumn = "id", entity = MeasureEntity::class)
    val measure: MeasureEntity
) {
}