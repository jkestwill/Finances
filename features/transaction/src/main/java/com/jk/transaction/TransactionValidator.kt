package com.jk.transaction

import com.jk.common_data.FinanceHelperException
import com.jk.common_data.Validator
import com.jk.common_data.sha256
import com.jk.transaction_common_ui.TransactionUI
import java.time.LocalDateTime
import javax.inject.Inject

class TransactionValidator @Inject constructor(
    private val operationValidator: OperationValidator
):Validator<TransactionUI.Builder> {
    private val scope = "transaction_validator"
    override fun validate(target: TransactionUI.Builder) {
        val preBuild = target.build()
        operationValidator.validate(preBuild.operation.builder())
        when {
            preBuild.type.name.isEmpty() -> {
                throw IllegalArgumentException("Transaction type must not be empty")
            }
            preBuild.date > LocalDateTime.now() ->{
                throw FinanceHelperException("Transaction date can't be later than now","")
            }
            preBuild.id.isEmpty() -> {
                target.id("${preBuild.date}${preBuild.operation.name}${preBuild.operation.money.amount}".sha256())
            }
        }
    }



}