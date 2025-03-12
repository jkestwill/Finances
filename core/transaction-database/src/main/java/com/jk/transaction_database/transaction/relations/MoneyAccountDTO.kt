package com.jk.transaction_database.transaction.relations

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class MoneyAccountDTO(
    @ColumnInfo("money_account_id")
    val id:String,
    @ColumnInfo("money_account_name")
    val name:String,
    @Embedded
    val moneyDto:MoneyDTO
)
