package com.jk.category_common_ui

import com.jk.category_common_data.TransactionCategory



fun TransactionCategory.toUI(): CategoryUI {
    return CategoryUI(id = id, name = name, color = color,)
}

fun CategoryUI.toCategory(): TransactionCategory {
    return TransactionCategory(id, name, color)
}