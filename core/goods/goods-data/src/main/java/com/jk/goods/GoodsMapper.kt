package com.jk.goods

import com.jk.common_goods_data.Goods
import com.jk.transaction_database.transaction.relations.GoodsxSpecificationsxMoneyRelation
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper()
// todo поменять маппер в goods
interface GoodsMapper {
    @Mapping(target = "specifications", source = "specifications")
    fun toGoods(goods:GoodsxSpecificationsxMoneyRelation):Goods

    fun toGoodsxSpecificationsxMoneyRelation(goods:Goods):GoodsxSpecificationsxMoneyRelation
}

