package com.jk.goods_common_ui

import com.jk.common_goods_data.Goods
import com.jk.common_goods_data.GoodsPreview
import com.jk.goods_common_ui.GoodsMoneyDateUI
import com.jk.goods_common_ui.GoodsPreviewUI
import org.mapstruct.Mapper

@Mapper
interface GoodsUIMapper {

    fun toGoods(goodsUI: GoodsMoneyDateUI):Goods

    fun toUI(goods:Goods):GoodsMoneyDateUI

    fun toPreviewUI(goodsPreview:GoodsPreview):GoodsPreviewUI

    fun toPreview(goodsPreviewUI: GoodsPreviewUI):GoodsPreview
}