package com.jk.category_common_ui

import com.jk.category_common_data.Category



fun Category.toUI(): CategoryUI {
    return CategoryUI(id = id, name = name, color = color,)
}

fun CategoryUI.toCategory(): Category {
    return Category(id, name, color)
}