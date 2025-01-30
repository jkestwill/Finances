package com.jk.transaction_data

import com.jk.category_common_data.Category
import com.jk.common_goods_data.Goods
import com.jk.common_goods_data.Specification
import com.jk.money_common_data.Currency
import com.jk.money_common_data.Money
import com.jk.transaction_common_data.Operation
import com.jk.transaction_common_data.Schedule
import com.jk.transaction_common_data.Transaction
import com.jk.transaction_database.transaction.entity.OperationEntity
import com.jk.transaction_database.transaction.entity.SpecificationsEntity
import com.jk.transaction_database.transaction.entity.CategoryEntity
import com.jk.transaction_database.transaction.entity.CurrencyEntity
import com.jk.transaction_database.transaction.entity.TransactionEntity
import com.jk.transaction_database.transaction.entity.GoodsEntity
import com.jk.transaction_database.transaction.entity.MoneyEntity
import com.jk.transaction_database.transaction.entity.ScheduleEntity

//fun TransactionPreviewEntity.toPreview(): TransactionPreview {
//    return TransactionPreview(
//        id = this.transaction.id,
//        operation = operationRelation.toPreview(),
//        type = typeRelation.name,
//        date = transaction.date
//    )
//}


//fun OperationPreviewEntity.toPreview(): OperationPreview {
//    return OperationPreview(
//        id = operation.id,
//        money = this.moneyRelation.toMoney(),
//        name = operation.name
//    )
//}
//
//
//fun MoneyRelation.toMoney(): Money {
//    return Money(
//        id = money.id,
//        currency = currency.toCurrency(),
//        amount = money.amount
//    )
//}

fun CurrencyEntity.toCurrency(): Currency {
    return Currency(id = id, name = name)
}

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(id = id, date = date, operationId = operation.id, typeId = type.id)
}

fun Operation.toOperationEntity(): OperationEntity {
    return OperationEntity(id = id, name = name, moneyId = money.id,isExpenses = true)
}

//fun Transaction.toRelation(): TransactionRelation {
//    return TransactionRelation(
//        this.toEntity(),
//        operation = this.operation.toRelation(),
//        type = TransactionTypeEntity(id = type.id, name = type.name)
//    )
//}
//
//fun Operation.toRelation(): OperationRelation {
//    return OperationRelation(
//        operation = toOperationEntity(),
//        categoryList = categoryList.map { it.toEntity() },
//        goodsList = goodsList.map { it.toRelation() },
//        cost = money.toRelation(),
//        schedule = scheduleList.map { it.toEntity() })
//}

fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        color = color.toString()
    )
}
//
//fun Goods.toRelation(): GoodsRelation {
//    return GoodsRelation(
//        goodsEntity = toEntity(),
//        specificationList = this.specifications.map { it.toRelation() },
//        cost = cost.toRelation()
//    )
//}

fun Goods.toEntity(): GoodsEntity {
    return GoodsEntity(id = id, name = name, amount = amount, costId = cost.id)
}

fun Specification.toEntity(): SpecificationsEntity {
    return SpecificationsEntity(id = id, text = text, amount = amount, measureId = measure.id)
}

//fun Specification.toRelation(): SpecificationRelation {
//    return SpecificationRelation(specificationEntity = toEntity(), measure = measure.toRelation())
//}
//
//fun Measure.toRelation(): MeasureRelation {
//    return MeasureRelation(measureEntity = toEntity(), lang = language.toEntity())
//}
//
//fun Measure.toEntity(): MeasureEntity {
//    return MeasureEntity(id = id, langId = language.id)
//}
//
//fun Language.toEntity(): LanguageEntity {
//    return LanguageEntity(id, lanName, lanShortName, text)
//}
//
//fun Money.toRelation(): MoneyRelation {
//    return MoneyRelation(
//        money = toEntity(),
//        currency = CurrencyEntity(id = currency.id, name = currency.name)
//    )
//}

fun Money.toEntity(): MoneyEntity {
    return MoneyEntity(id = id, amount = amount, currencyId = currency.id)
}

fun Schedule.toEntity(): ScheduleEntity {
    return ScheduleEntity(
        id = id,
        startDate = dateStart,
        countLeft = countLeft,
        periodMillis = repeatPeriodMillis,
        time = time,
        day = day,
        week = week,
        month = month
    )
}