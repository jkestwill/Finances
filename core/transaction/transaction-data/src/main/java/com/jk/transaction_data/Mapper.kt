package com.jk.transaction_data

import com.jk.transaction_common_data.Operation
import com.jk.transaction_common_data.OperationPreview
import com.jk.transaction_common_data.Transaction
import com.jk.transaction_common_data.TransactionMoney
import com.jk.transaction_common_data.TransactionPreview
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.TransactionEntity
import com.jk.transaction_database.transaction.preview.OperationPreviewEntity
import com.jk.transaction_database.transaction.preview.TransactionPreviewEntity
import com.jk.transaction_database.transaction.relations.MoneyRelation

fun TransactionPreviewEntity.toPreview(): TransactionPreview {
    return TransactionPreview(
        id = this.transaction.id,
        operation = operationRelation.toPreview(),
        type = typeRelation.name,
        date = transaction.date
    )
}


fun OperationPreviewEntity.toPreview(): OperationPreview {
    return OperationPreview(
        id = operation.id,
        money = this.moneyRelation.toMoney(),
        name = operation.name
    )
}


fun MoneyRelation.toMoney(): TransactionMoney {
    return TransactionMoney(
        id = money.id,
        currency = currency.name,
        amount = money.amount
    )
}


fun Transaction.toEntity(): TransactionEntity {
   return TransactionEntity(id = id, date = date, operationId = operation.id, typeId = type.id)
}

fun Operation.toOperationEntity(): OperationEntity {
    return OperationEntity(id=id,name=name, moneyId = money.id)
}