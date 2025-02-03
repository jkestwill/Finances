package com.jk.transaction.validator

import com.jk.common_data.Validator
import com.jk.common_data.sha256
import com.jk.money_common_ui.MoneyUI
import com.jk.transaction_common_ui.OperationUI
import javax.inject.Inject

class OperationValidator @Inject constructor(
    private val moneyValidator: Validator<MoneyUI>
):Validator<OperationUI.Builder> {
    override fun validate(target: OperationUI.Builder) {
        val preBuild = target.build()
        moneyValidator.validate(preBuild.money)
        when {
            preBuild.id.isEmpty() -> {
             target.id("${preBuild.categoryList.size}${preBuild.goodsList.size}${preBuild.name}${preBuild.scheduleList.size}".sha256())
            }

            preBuild.name.isEmpty() -> {
                throw IllegalArgumentException("Transaction name can't be empty")
            }
        }
    }
}