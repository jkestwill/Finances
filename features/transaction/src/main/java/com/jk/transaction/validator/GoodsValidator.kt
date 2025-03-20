package com.jk.transaction.validator

import com.jk.common_data.Validator
import com.jk.goods_common_ui.exceptions.GoodsNameLengthException
import com.jk.goods_common_ui.models.GoodsUI
import com.jk.money_common_ui.MoneyUI
import javax.inject.Inject

class GoodsValidator @Inject constructor(private val moneyValidator: Validator<MoneyUI>) :
    Validator<GoodsUI> {
    override fun validate(target: GoodsUI) {
        val preBuild = target
        moneyValidator.validate(preBuild.cost)

        when {
            preBuild.name.isEmpty() -> {
                //"Goods name can't be empty"
                throw GoodsNameLengthException()
            }
        }

    }
}

