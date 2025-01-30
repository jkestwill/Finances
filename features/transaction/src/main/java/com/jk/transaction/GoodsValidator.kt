package com.jk.transaction

import com.jk.common_data.Validator
import com.jk.goods_common_ui.GoodsUI
import javax.inject.Inject

class GoodsValidator @Inject constructor(private val moneyValidator: MoneyValidator) :
    Validator<GoodsUI.Builder> {
    override fun validate(target: GoodsUI.Builder) {
        // todo взять из AddNewTransactionViewModel код и вставить сюда
    }
}