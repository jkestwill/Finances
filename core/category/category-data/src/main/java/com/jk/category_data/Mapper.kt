package com.jk.category_data

import com.jk.category_common_data.TransactionCategory
import com.jk.transaction_database.transaction.CategoryEntity

fun TransactionCategory.toEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        color = color.toString(),
    )
}

fun CategoryEntity.toCategory(): TransactionCategory {
    return TransactionCategory(id, name, color.toULong())
}