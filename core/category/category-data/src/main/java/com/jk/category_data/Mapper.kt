package com.jk.category_data

import com.jk.category_common_data.Category
import com.jk.transaction_database.transaction.entity.CategoryEntity

fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        color = color.toString(),
    )
}

fun CategoryEntity.toCategory(): Category {
    return Category(id, name, color.toULong())
}