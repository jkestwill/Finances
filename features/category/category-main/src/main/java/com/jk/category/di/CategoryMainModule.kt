package com.jk.category.di

import com.jk.category.CategoryUIMapper
import com.jk.category.CategoryUIMapperImpl

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