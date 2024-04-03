package com.jk.financehelper.utils


import com.jk.financehelper.domain.model.preview.OperationPreview
import com.jk.financehelper.domain.model.preview.TransactionPreview
import com.jk.transaction_database.transaction.preview.OperationPreviewDatabase
import com.jk.transaction_database.transaction.preview.TransactionPreviewDatabase

fun TransactionPreviewDatabase.toPreview(): TransactionPreview {
    return TransactionPreview(
        id=transaction.id,
        operation=this.operation.toPreview(),
        date = this.transaction.date,
        type=this.type.toType()
    )
}

fun OperationPreviewDatabase.toPreview(): OperationPreview {
    return OperationPreview(
        id=operation.id,
        money = this.moneyRelation.toMoney(),
        name=operation.name
    )
}