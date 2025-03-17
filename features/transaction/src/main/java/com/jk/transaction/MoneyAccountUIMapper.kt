package com.jk.transaction

import com.jk.money_common_data.Money
import com.jk.money_common_data.MoneyAccount
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI
import com.jk.transaction_common_ui.MoneyAccountUI
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper
interface MoneyAccountUIMapper {

    @Mapping(target = "moneyUI", source = "money", qualifiedByName = ["moneyToMoneyUI"])
    fun toUI(moneyAccount: MoneyAccount): MoneyAccountUI

    fun toMoneyAccount(moneyAccountUI: MoneyAccountUI): MoneyAccount

    @Named("moneyToMoneyUI")
    fun moneyToMoneyUI(money: Money): MoneyUI {
        return MoneyUI(
            id = money.id,
            amount =  money.amount,
            currency = CurrencyUI(money.currency.id, money.currency.id)
        )
    }
}