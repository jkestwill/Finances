package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.jk.transaction_database.transaction.entity.AddressEntity
import com.jk.transaction_database.transaction.entity.GoodsEntity
import com.jk.transaction_database.transaction.entity.StoreEntity


@Entity(
    "store_goods_list",
    primaryKeys = ["store_id", "goods_id"],
    foreignKeys = [
        ForeignKey(
            entity = GoodsEntity::class,
            parentColumns = ["id"],
            childColumns = ["goods_id"]
        ),
        ForeignKey(
            entity = StoreEntity::class,
            parentColumns = ["id"],
            childColumns = ["store_id"]
        )
    ]
)
data class StoreGoodsListEntity(
    @ColumnInfo("store_id")
    val storeId:String,
    @ColumnInfo("goods_id")
    val goodsId:String
)