package com.jk.goods

import com.jk.common_goods_data.Goods
import com.jk.transaction_database.transaction.entity.GoodsEntity
import com.jk.transaction_database.transaction.entity.MoneyEntity
import com.jk.transaction_database.transaction.entity.SpecificationsEntity
import com.jk.transaction_database.transaction.relations.GoodsxSpecificationsxMoneyRelation
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.AfterMapping
import org.mapstruct.Named

@Mapper(uses = [SpecificationsMapper::class,MoneyMapper::class])
interface GoodsMapper {
    @Mapping(target = "id", source = "goodsEntity.id")
    @Mapping(target = "name", source = "goodsEntity.name")
    @Mapping(target = "amount", source = "goodsEntity.amount")
    fun toGoods(goods: GoodsxSpecificationsxMoneyRelation): Goods

    @Mapping(target = "goodsEntity", source = "goods", qualifiedByName = ["toEntity"])
    fun toGoodsxSpecificationsxMoneyRelation(goods: Goods): GoodsxSpecificationsxMoneyRelation {
        val goodsEntity =  GoodsEntity(
            goods.id,
            goods.name,
            goods.amount,
            goods.cost.id
        )
        return GoodsxSpecificationsxMoneyRelation(
            goodsEntity = goodsEntity,
            specifications = goods.specifications.map {
                SpecificationsEntity(
                    id = it.id,
                    text = it.text,
                    amount = it.amount,
                    measureId = it.measure.id
                )
            },
            cost = MoneyEntity(
                id = goods.cost.id,
                amount = goods.cost.amount,
                goods.cost.currency.id
            )
        )
    }


    @Mapping(source = "id", target = "id")
    fun toGoods(goodsEntity: GoodsEntity): Goods

    @Mapping(target = "id", source = "id")
    @Mapping(target = "costId", source = "cost.id")
    @Named("toEntity")
    fun toEntity(goods: Goods): GoodsEntity
}

