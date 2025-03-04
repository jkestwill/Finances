package com.jk.transaction_database.transaction.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "money",
    foreignKeys = [ForeignKey(
        entity = CurrencyEntity::class,
        parentColumns = ["id"],
        childColumns = ["currency_id"]
    )]
)
// var т.к @Ignore все руинит
data class MoneyEntity public constructor(
    @PrimaryKey
    var id: String,
    var amount: Double,
    @ColumnInfo("currency_id")
    var currencyId: String,
    @Ignore
    var date:LocalDate?

){
    constructor():this("",0.0,"", null)
}