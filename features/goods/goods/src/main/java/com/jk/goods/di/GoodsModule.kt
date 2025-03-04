package com.jk.goods.di

import com.jk.goods_common_ui.GoodsUIMapper
import com.jk.goods_common_ui.GoodsUIMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class GoodsModule {

    @Provides
    fun provideGoodsUIMapper():GoodsUIMapper = GoodsUIMapperImpl()
}