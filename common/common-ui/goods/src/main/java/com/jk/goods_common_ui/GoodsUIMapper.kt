package com.jk.goods_common_ui

import com.jk.common_goods_data.Goods
import com.jk.common_goods_data.GoodsPreview
import org.mapstruct.Mapper

@Mapper
interface GoodsUIMapper {

    fun toGoods(goodsUI: GoodsUI):Goods

    fun toUI(goods:Goods):GoodsUI


    fun toPreviewUI(goodsPreview:GoodsPreview):GoodsPreviewUI

    fun toPreview(goodsPreviewUI: GoodsPreviewUI):GoodsPreview
}