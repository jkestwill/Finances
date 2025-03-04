package com.jk.transaction.validator

import com.jk.common_data.Validator
import com.jk.goods_common_ui.GoodsMoneyDateUI
import javax.inject.Inject

class GoodsValidator @Inject constructor(private val moneyValidator: MoneyValidator) :
    Validator<GoodsMoneyDateUI.Builder> {
    override fun validate(target: GoodsMoneyDateUI.Builder) {
        val preBuild = target.build()
       // for (i in preBuild.cost)
       // moneyValidator.validate(i)
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