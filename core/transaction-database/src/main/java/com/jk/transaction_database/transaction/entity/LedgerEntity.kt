package com.jk.transaction_database.transaction.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName="ledger")
data class LedgerEntity(
    @PrimaryKey
    val id:String,
    val name:String,
    @ColumnInfo(name="date_created")
    val dateCreated: LocalDate
) {
}