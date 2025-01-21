package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.jk.transaction_database.transaction.GoodsEntity
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.TransactionCategoryDatabaseEntity

// add ledger
@Entity(
    tableName="goods_list",
    foreignKeys = [
        ForeignKey(
            OperationEntity::class,
            parentColumns = ["id"],
            childColumns = ["operation_id"]
        ),
        ForeignKey(
            GoodsEntity::class,
            parentColumns = ["id"],
            childColumns = ["goods_id"],
        ),
    ]

)
data class OperationGoodsListEntity(
    @ColumnInfo(name = "operation_id")
    val operationId: String,
    @ColumnInfo(name = "goods_id")
    val goodsId: String
)