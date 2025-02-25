package com.jk.category.di

import com.jk.category.CategoryUIMapper
import com.jk.category.CategoryUIMapperImpl
import com.jk.category.add_new_category.CategoryUIValidator

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CategoryMainModule {

    @Provides
    fun provideCategoryUIMapper(): CategoryUIMapper = CategoryUIMapperImpl()

    @Provides
    fun provideCategoryUIValidator():CategoryUIValidator = CategoryUIValidator()
}