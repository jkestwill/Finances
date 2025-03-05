package com.jk.goods.mapper

import com.jk.money_common_data.Money
import com.jk.transaction_database.transaction.entity.MoneyEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface MoneyMapper {
    @Mapping(target = "id", source = "money.id")
    @Mapping(target = "amount", source = "money.amount")
    @Mapping(target = "currencyId", source = "money.currency.id")
    fun toEntity(money:Money):MoneyEntity

    @Mapping(target = "id", source = "moneyEntity.id")
    @Mapping(target = "amount", source = "moneyEntity.amount")
    @Mapping(target = "currency.id", source = "moneyEntity.currencyId")
    @Mapping(target = "currency.name",  expression = "java(\"\")")
    fun toMoney(moneyEntity: MoneyEntity):Money
}