package com.jk.transaction_data.mapper

import com.jk.money_common_data.Money
import com.jk.transaction_common_data.Operation
import com.jk.transaction_common_data.Transaction
import com.jk.transaction_database.transaction.entity.MoneyEntity
import com.jk.transaction_database.transaction.entity.OperationEntity
import com.jk.transaction_database.transaction.relations.TransactionxCategoriesxTypexGoods
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper
interface TransactionMapper {

    fun toTransaction(transactionxCategoriesxTypexGoods: TransactionxCategoriesxTypexGoods):Transaction

    @Mapping(target = "operation.operationEntity", source = "transaction.operation", qualifiedByName = ["operationToOperationEntity"])
    @Mapping(target = "operation.money", qualifiedByName = ["moneyToMoneyEntity"])
    fun toTransactionxCategoryXTypeXGoods(transaction: Transaction):TransactionxCategoriesxTypexGoods

    @Named("operationToOperationEntity")
    fun oprationToOperationEntity(operation: Operation): OperationEntity {
        return OperationEntity(id=operation.id,name = operation.name, moneyId = operation.money.id, isExpenses = true)
    }

    @Named("moneyToMoneyEntity")
    fun moneyToMoneyEntity(money: Money):MoneyEntity{
        return MoneyEntity(money.id,money.amount,money.currency.id,null)
    }
}