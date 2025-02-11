package com.jk.category

import com.jk.category_common_data.Category
import com.jk.category_common_ui.CategoryUI
import org.mapstruct.Mapper

@Mapper()
interface CategoryUIMapper {

    fun toCategory(categoryUI: CategoryUI): Category


    fun toCategoryUI(category: Category): CategoryUI


}