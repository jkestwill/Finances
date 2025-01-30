package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.jk.transaction_database.transaction.entity.GoodsEntity
import com.jk.transaction_database.transaction.entity.SpecificationsEntity

@Entity("goods_specifications_list", primaryKeys = ["goods_id","specifications_id"], foreignKeys = [
    ForeignKey(
        entity = GoodsEntity::class,
        childColumns  = ["goods_id"],
        parentColumns = ["id"],
    ),
    ForeignKey(
        entity = SpecificationsEntity::class,
        childColumns  = ["specifications_id"],
        parentColumns = ["id"],
    )
])
data class GoodsSpecificationsListEntity(
    @ColumnInfo("goods_id")
    val goodsId:String,
    @ColumnInfo("specifications_id")
    val specificationsId:String
)
