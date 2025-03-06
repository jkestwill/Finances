package com.jk.goods.di

import com.jk.common_data.Validator
import com.jk.goods_common_ui.GoodsUIMapper
import com.jk.goods_common_ui.GoodsUIMapperImpl
import com.jk.goods_common_ui.models.GoodsUI
import com.jk.money_common_ui.CurrencyUIMapper
import com.jk.money_common_ui.CurrencyUIMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class GoodsModule {

    @Provides
    fun provideGoodsUIMapper():GoodsUIMapper = GoodsUIMapperImpl()

    @Provides
    fun provideCurrencyUIMapper():CurrencyUIMapper = CurrencyUIMapperImpl()


}