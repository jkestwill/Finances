package com.jk.transaction.validator

import com.jk.common_data.Validator
import com.jk.goods_common_ui.GoodsUI
import javax.inject.Inject

class GoodsValidator @Inject constructor(private val moneyValidator: MoneyValidator) :
    Validator<GoodsUI.Builder> {
    override fun validate(target: GoodsUI.Builder) {
        val preBuild = target.build()
        moneyValidator.validate(preBuild.cost)
        when {
            preBuild.name.isEmpty() -> {
                throw IllegalArgumentException("Goods name can't be empty")
            }

            preBuild.amount < 0 -> {
                throw IllegalArgumentException("Goods count can't be empty")
            }
        }

    }
}