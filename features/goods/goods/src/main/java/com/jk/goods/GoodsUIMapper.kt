package com.jk.goods

import com.jk.common_goods_data.Goods
import com.jk.goods_common_ui.GoodsUI
import org.mapstruct.Mapper

@Mapper
interface GoodsUIMapper {

    fun toGoods(goodsUI: GoodsUI):Goods

    fun toUI(goods:Goods):GoodsUI
}