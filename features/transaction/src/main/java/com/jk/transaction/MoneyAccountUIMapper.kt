package com.jk.transaction

import com.jk.money_common_data.MoneyAccount
import com.jk.transaction_common_ui.MoneyAccountUI
import org.mapstruct.Mapper

@Mapper
interface MoneyAccountUIMapper {

    fun toUI(moneyAccount:MoneyAccount):MoneyAccountUI

    fun toMoneyAccount(moneyAccountUI: MoneyAccountUI):MoneyAccount
}