package com.jk.transaction.di

import com.jk.common_data.Validator
import com.jk.goods_common_ui.models.GoodsUI
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI
import com.jk.transaction.MoneyAccountUIMapper
import com.jk.transaction.MoneyAccountUIMapperImpl
import com.jk.transaction.TransactionStringResourceExceptionHandler
import com.jk.transaction.validator.CurrencyValidator
import com.jk.transaction.validator.GoodsValidator
import com.jk.transaction.validator.MoneyValidator
import com.jk.transaction_common_ui.TransactionUiMapper
import com.jk.transaction_common_ui.TransactionUiMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import android.content.Context
import com.jk.common_ui.StringResourceExceptionHandler
import com.jk.common_ui.UIException

@Module
@InstallIn(SingletonComponent::class)
class TransactionModule {

    @Provides
    fun providesTransactionUIMapper(): TransactionUiMapper = TransactionUiMapperImpl()

    @Provides
    fun providesMoneyValidator(currencyValidator: Validator<CurrencyUI>): Validator<MoneyUI> =
        MoneyValidator(currencyValidator)


    @Provides
    fun providesCurrencyValidator(): Validator<CurrencyUI> =
        CurrencyValidator()

    @Provides
    fun provideGoodsUIValidator(moneyValidator: Validator<MoneyUI>): Validator<GoodsUI> =
        GoodsValidator(moneyValidator)

    @Provides
    fun provideMoneyAccountUIMapper(): MoneyAccountUIMapper = MoneyAccountUIMapperImpl()

    @Provides
    fun provideTransactionStringResourceExceptionHandler(context: Context): StringResourceExceptionHandler<UIException> =
        TransactionStringResourceExceptionHandler(context)
}