package com.jk.transaction.validator

import com.jk.common_data.Validator
import com.jk.common_data.exceptions.TransactionDateAfterNowException
import com.jk.common_data.exceptions.TransactionTypeEmptyException
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
                throw TransactionTypeEmptyException()
            }
            preBuild.date > LocalDateTime.now() ->{
                throw TransactionDateAfterNowException()
            }
        }
    }



}