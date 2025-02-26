package com.jk.category_data

import com.jk.category_common_data.Category
import com.jk.transaction_database.transaction.entity.CategoryEntity
import org.mapstruct.Mapper

@Mapper
interface CategoryMapper{


    fun toEntity(category: Category):CategoryEntity


    fun toCategory(entity: CategoryEntity):Category

}

