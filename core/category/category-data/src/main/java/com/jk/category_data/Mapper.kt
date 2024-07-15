package com.jk.category_data

import com.jk.category_common_data.TransactionCategory
import com.jk.transaction_database.transaction.TransactionCategoryDatabaseEntity

fun TransactionCategory.toEntity(): TransactionCategoryDatabaseEntity {
    return TransactionCategoryDatabaseEntity(
        id = id,
        name = name,
        color = color.toString(),
        isExpenses = isExpenses
    )
}

fun TransactionCategoryDatabaseEntity.toCategory(): TransactionCategory {
    return TransactionCategory(id, name, color.toULong(), isExpenses)
}