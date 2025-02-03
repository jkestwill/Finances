package com.jk.category_common_ui

import com.jk.category_common_data.Category
import org.mapstruct.Mapper

@Mapper()
interface CategoryUIMapper {

    fun toCategory(categoryUI: CategoryUI): Category


    fun toCategoryUI(category: Category): CategoryUI
}