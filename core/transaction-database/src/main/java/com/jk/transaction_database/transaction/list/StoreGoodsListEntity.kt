package com.jk.transaction_database.transaction.list

import androidx.room.Entity
import androidx.room.ForeignKey
import com.jk.transaction_database.transaction.entity.GoodsEntity
import com.jk.transaction_database.transaction.entity.StoreEntity

@Entity(
    tableName = "store_goods_list",
    primaryKeys = ["store_id", "goods_id"],
    foreignKeys = [ForeignKey(
        entity = StoreEntity::class,
        parentColumns = ["id"],
        childColumns = ["store_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = GoodsEntity::class,
        parentColumns = ["id"],
        childColumns = ["goods_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )
    ]
)
data class StoreGoodsListEntity(
    val storeId: String,
    val goodsId: String
)

