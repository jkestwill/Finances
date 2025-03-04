package com.jk.goods

import com.jk.common_goods_data.Goods
import com.jk.common_goods_data.GoodsPreview
import com.jk.transaction_database.transaction.entity.GoodsEntity
import com.jk.transaction_database.transaction.entity.MoneyEntity
import com.jk.transaction_database.transaction.entity.SpecificationsEntity
import com.jk.transaction_database.transaction.preview.GoodsPreviewEntity
import com.jk.transaction_database.transaction.relations.GoodsxSpecificationsxMoneyRelation
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper(uses = [SpecificationsMapper::class, MoneyMapper::class])

interface GoodsMapper {
    @Mapping(target = "id", source = "goodsEntity.id")
    @Mapping(target = "name", source = "goodsEntity.name")
    @Mapping(target = "amount", source = "goodsEntity.amount")
    fun toGoods(goods: GoodsxSpecificationsxMoneyRelation): Goods


    fun toGoodsxSpecificationsxMoneyRelation(goods: Goods): GoodsxSpecificationsxMoneyRelation {
        val goodsEntity = GoodsEntity(
            goods.id,
            goods.name,
            goods.amount,
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
            cost =  MoneyEntity(
                id = goods.cost.id,
                amount = goods.cost.amount,
                currencyId = goods.cost.currency.id,
                date = goods.cost.date
            )
        )
    }


    fun toPreview(goodsPreview: GoodsPreviewEntity):GoodsPreview

    @Mapping(source = "id", target = "id")
    fun toGoods(goodsEntity: GoodsEntity): Goods

    @Mapping(target = "id", source = "id")
    @Named("toEntity")
    fun toEntity(goods: Goods): GoodsEntity
}

