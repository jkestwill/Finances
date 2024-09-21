package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.jk.transaction_database.transaction.AddressEntity
import com.jk.transaction_database.transaction.BankEntity

data class BankRelation(
    @Embedded
    val bankEntity:BankEntity,
    @Relation(
        parentColumn = "address_id",
        entityColumn = "id",
        entity = AddressEntity::class
    )
    val addressEntity: AddressEntity
)