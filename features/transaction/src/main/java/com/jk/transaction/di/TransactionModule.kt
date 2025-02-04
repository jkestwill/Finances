package com.jk.transaction.di

import com.jk.common_data.Validator
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI
import com.jk.transaction.validator.CurrencyValidator
import com.jk.transaction.validator.MoneyValidator
import com.jk.transaction_common_ui.TransactionUiMapper
import com.jk.transaction_common_ui.TransactionUiMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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
}