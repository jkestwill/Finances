package com.jk.transaction_database.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "goods",foreignKeys = [
        ForeignKey(
            entity = MoneyEntity::class,
            childColumns  = ["cost_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GoodsEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val amount: Int,
    @ColumnInfo("cost_id")
    val costId: String
)