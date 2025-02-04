package com.jk.category.di

import com.jk.category_common_ui.CategoryUIMapper
import com.jk.category_common_ui.CategoryUIMapperImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CategoryMainModule {

    @Provides
    fun bindCategoryUIMapper(): CategoryUIMapper = CategoryUIMapperImpl()
}