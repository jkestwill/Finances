package com.jk.transaction_database.transaction.list

import androidx.room.Entity
import androidx.room.ForeignKey
import com.jk.transaction_database.transaction.entity.GoodsEntity
import com.jk.transaction_database.transaction.entity.MoneyEntity

@Entity(
    "goods_money_list", primaryKeys = ["goods_id", "money_id"],
    foreignKeys = [
        ForeignKey(
            entity = GoodsEntity::class,
            parentColumns = ["id"],
            childColumns = ["goodsId"]
        ),
        ForeignKey(
            entity = MoneyEntity::class,
            parentColumns = ["id"],
            childColumns = ["moneyId"]
        )]
)
data class GoodsMoneyListEntity(
    val goodsId: String,
    val moneyId: String
)