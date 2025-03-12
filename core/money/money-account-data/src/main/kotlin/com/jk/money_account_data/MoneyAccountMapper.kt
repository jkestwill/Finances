package com.jk.money_account_data

import com.jk.money_common_data.Currency
import com.jk.money_common_data.Money
import com.jk.money_common_data.MoneyAccount
import com.jk.transaction_database.transaction.entity.CurrencyEntity
import com.jk.transaction_database.transaction.entity.MoneyAccountEntity
import com.jk.transaction_database.transaction.entity.MoneyEntity
import com.jk.transaction_database.transaction.relations.MoneyAccountDTO
import com.jk.transaction_database.transaction.relations.MoneyDTO
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper()
interface MoneyAccountMapper {
    @Mapping(target = "money", source = "moneyDto", qualifiedByName = ["moneyDTOToMoney"])
    fun toMoneyAccount(moneyAccountDTO: MoneyAccountDTO):MoneyAccount

    fun toEntity(moneyAccount:MoneyAccount):MoneyAccountEntity

    @Mapping(target = "moneyDto", source = "money", qualifiedByName = ["moneyToMoneyDTO"])
    fun toDTO(moneyAccount:MoneyAccount):MoneyAccountDTO

    @Named("moneyDTOToMoney")
    fun moneyDTOToMoney(money:MoneyDTO):Money{
        return Money(id=money.moneyEntity.id, amount = money.moneyEntity.amount, currency = Currency(money.currency.id,money.currency.name))
    }
    @Named("moneyToMoneyDTO")
    fun moneyToMoneyDTO(money:Money):MoneyDTO{
        return MoneyDTO(
            moneyEntity = MoneyEntity(money.id, money.amount, money.currency.id,null),
            currency = CurrencyEntity(money.currency.id,money.currency.name)
        )
    }
}