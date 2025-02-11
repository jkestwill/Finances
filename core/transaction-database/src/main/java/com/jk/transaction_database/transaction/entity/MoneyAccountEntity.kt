package com.jk.transaction_database.transaction.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    "money_account",
    foreignKeys = [ForeignKey(
        entity = MoneyEntity::class,
        parentColumns = ["id"],
        childColumns = ["money_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class MoneyAccountEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    @ColumnInfo("img_path")
    val imgPath: String?,
    @ColumnInfo("money_id")
    val moneyId: String
)