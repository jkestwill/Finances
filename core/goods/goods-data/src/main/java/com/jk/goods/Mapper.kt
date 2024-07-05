package com.jk.goods

import android.widget.GridLayout.Spec
import com.jk.money_data.Currency
import com.jk.money_data.Money
import com.jk.transaction_database.transaction.LanguageEntity
import com.jk.transaction_database.transaction.MeasureEntity
import com.jk.transaction_database.transaction.SpecificationsEntity
import com.jk.transaction_database.transaction.TransactionCurrencyDatabaseEntity
import com.jk.transaction_database.transaction.TransactionGoodsDatabaseEntity
import com.jk.transaction_database.transaction.TransactionMoneyDatabaseEntity
import com.jk.transaction_database.transaction.relations.GoodsRelation
import com.jk.transaction_database.transaction.relations.MeasureRelation
import com.jk.transaction_database.transaction.relations.MoneyRelation
import com.jk.transaction_database.transaction.relations.SpecificationRelation

fun Goods.toGoodsRelation(): GoodsRelation {
    return GoodsRelation(
        goodsEntity = TransactionGoodsDatabaseEntity(
            id = id,
            name = name,
            costId = cost.id,
            amount = amount
        ),
        specificationList = specifications.map { it.toSpecificationRelation() },
        cost = MoneyRelation(
            money = TransactionMoneyDatabaseEntity(
                id = cost.id,
                cost.amount,
                cost.currency.id
            ),
            currency = TransactionCurrencyDatabaseEntity(id = cost.currency.id, cost.currency.name)
        )
    )
}


fun Specification.toSpecificationRelation(): SpecificationRelation {
    return SpecificationRelation(
        specificationEntity = SpecificationsEntity(
            id = id,
            text = text,
            amount = amount,
            measureId = measure.id
        ),
        measure = measure.toMeasureRelation()
    )
}

fun Measure.toMeasureRelation(): MeasureRelation {
    return MeasureRelation(
        measureEntity = MeasureEntity(
            id = id,
            language.id
        ),
        lang = LanguageEntity(
            id = language.id,
            language.lanName,
            language.lanShortName,
            language.text
        )
    )
}

fun GoodsRelation.toGoods(): Goods {
    return Goods(
        id = this.goodsEntity.id,
        name = goodsEntity.name,
        amount = goodsEntity.amount,
        specifications = this.specificationList.map { it.toSpecification() },
        cost = Money(
            cost.money.id,
            amount = cost.money.amount,
            currency = Currency(cost.currency.id, cost.currency.name)
        )
    )
}

fun SpecificationRelation.toSpecification(): Specification {
    return Specification(
        id = specificationEntity.id,
        text = specificationEntity.text,
        amount = specificationEntity.amount,
        measure = Measure(measure.measureEntity.id, language = measure.lang.toLanguage())
    )
}

fun LanguageEntity.toLanguage(): Language {
    return Language(id = id, lanName = langName, lanShortName = langShortName, text = text)
}

