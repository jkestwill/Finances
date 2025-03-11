package com.jk.transaction_data.mapper

import com.jk.category_common_data.Category
import com.jk.common_goods_data.Goods
import com.jk.common_goods_data.GoodsPurchase
import com.jk.money_common_data.Money
import com.jk.transaction_common_data.Operation
import com.jk.transaction_common_data.Transaction
import com.jk.transaction_database.transaction.entity.CategoryEntity
import com.jk.transaction_database.transaction.entity.GoodsEntity
import com.jk.transaction_database.transaction.entity.MoneyEntity
import com.jk.transaction_database.transaction.entity.OperationEntity
import com.jk.transaction_database.transaction.entity.TransactionEntity
import com.jk.transaction_database.transaction.relations.OperationRelation
import com.jk.transaction_database.transaction.relations.TransactionxCategoriesxTypexGoods
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper
interface TransactionMapper {

    fun toTransaction(transactionxCategoriesxTypexGoods: TransactionxCategoriesxTypexGoods): Transaction

    @Mapping(
        target = "operation",
        source = "transaction.operation",
        qualifiedByName = ["operationToRelation"]
    )
    @Mapping(
        target = "transactionEntity",
        source = "transaction",
        qualifiedByName = ["transactionToTransactionEntity"]
    )
    @Mapping(
        target = "categoryList",
        source = "operation.categoryList",
        qualifiedByName = ["categoryToCategoryEntity"]
    )
    @Mapping(
        target = "goodsList",
        source = "operation.goodsList",
        qualifiedByName = ["goodsToGoodsEntity"]
    )
    @Mapping(target = "goodsAmount", source = "")
    fun toTransactionxCategoryXTypeXGoods(transaction: Transaction): TransactionxCategoriesxTypexGoods

    @Named("categoryToCategoryEntity")
    fun categoryToCategoryEntity(category: Category):CategoryEntity{
        return CategoryEntity(id=category.id, name = category.name, color = category.color)
    }

    @Named("operationToOperationEntity")
    fun operationToOperationEntity(operation: Operation): OperationEntity {

        return OperationEntity(
            id = operation.id,
            name = operation.name,
            moneyId = operation.money.id,
            isExpenses = operation.isExpenses
        )
    }
    @Named("goodsToGoodsEntity")
    fun goodsToGoodsEntity(goods:GoodsPurchase):GoodsEntity{
        return GoodsEntity(id = goods.id, name = goods.name)
    }

    @Named("moneyToMoneyEntity")
    fun moneyToMoneyEntity(money: Money): MoneyEntity {
        return MoneyEntity(money.id, money.amount, money.currency.id, null)
    }

    @Named("operationToRelation")
    fun operationToRelation(operation: Operation): OperationRelation {
        return OperationRelation(
            operationEntity = OperationEntity(
                id = operation.id,
                name = operation.name,
                moneyId = operation.money.id,
                isExpenses = operation.isExpenses
            ),
            money = moneyToMoneyEntity(money = operation.money)
        )
    }

    @Named("transactionToTransactionEntity")
    fun transactionToTransactionEntity(transaction: Transaction): TransactionEntity {
        return TransactionEntity(
            id = transaction.id,
            date = transaction.date,
            operationId = transaction.operation.id,
            typeId = transaction.type.id,
            moneyAccountId = transaction.moneyAccount.id
        )
    }
}