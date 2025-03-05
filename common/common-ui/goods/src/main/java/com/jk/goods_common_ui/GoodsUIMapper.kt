package com.jk.goods_common_ui

import com.jk.common_goods_data.Goods
import com.jk.common_goods_data.GoodsPreview
import com.jk.common_goods_data.GoodsPurchase
import com.jk.goods_common_ui.models.GoodsPreviewUI
import com.jk.goods_common_ui.models.GoodsPurchaseUI
import com.jk.goods_common_ui.models.GoodsUI
import org.mapstruct.Mapper

@Mapper
interface GoodsUIMapper {

    fun toGoods(goodsUI: GoodsUI):Goods

    fun toUI(goods:Goods): GoodsUI

    fun toGoodsPurchaseUI(goodsPurchase: GoodsPurchase):GoodsPurchaseUI

    fun toGoodsPurchase(goodsPurchase: GoodsPurchaseUI):GoodsPurchase

    fun toPreviewUI(goodsPreview:GoodsPreview): GoodsPreviewUI

    fun toPreview(goodsPreviewUI: GoodsPreviewUI):GoodsPreview
}