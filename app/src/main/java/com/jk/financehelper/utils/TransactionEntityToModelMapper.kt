package com.jk.financehelper.utils


import com.jk.category_data.TransactionCategory
import com.jk.financehelper.domain.model.Currency

import com.jk.financehelper.domain.model.TransactionMoney
import com.jk.financehelper.domain.model.TransactionType
import com.jk.transaction_database.transaction.TransactionCategoryDatabaseEntity
import com.jk.transaction_database.transaction.TransactionCurrencyDatabaseEntity
import com.jk.transaction_database.transaction.TransactionTypeDatabaseEntity
import com.jk.transaction_database.transaction.relations.MoneyRelation


fun TransactionCategoryDatabaseEntity.toCategory(): TransactionCategory {
    return TransactionCategory(id,name, color.toULong(),isExpenses)
}

fun MoneyRelation.toMoney():TransactionMoney{
    return TransactionMoney(
        id=money.id,
        currency = currency.toCurrency(),
        amount = money.amount
    )
}

fun TransactionCurrencyDatabaseEntity.toCurrency():Currency{
    return Currency(
        id=id,
        name=name
    )
}

fun TransactionTypeDatabaseEntity.toType():TransactionType{
    return TransactionType(
        id=id,name=name
    )
}