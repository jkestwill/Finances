package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.jk.transaction_database.transaction.entity.GoodsEntity
import com.jk.transaction_database.transaction.entity.MoneyEntity
import java.time.LocalDate

@Entity(
    "goods_money_list",
    primaryKeys = ["goods_id", "money_id"],
    foreignKeys = [
        ForeignKey(
            entity = GoodsEntity::class,
            parentColumns = ["id"],
            childColumns = ["goods_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MoneyEntity::class,
            parentColumns = ["id"],
            childColumns = ["money_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GoodsMoneyListEntity(
    @ColumnInfo(name="goods_id")
    val goodsId:String,
    @ColumnInfo(name = "money_id")
    val moneyId:String,
    val date:LocalDate
)