package com.jk.financehelper.utils

import com.jk.transaction_database.transaction.TransactionCategoryDatabaseEntity
import com.jk.transaction_database.transaction.TransactionGoodsDatabaseEntity
import com.jk.transaction_database.transaction.list.OperationCategoryList
import com.jk.transaction_database.transaction.list.TransactionGoodsList


fun List<TransactionCategoryDatabaseEntity>.toOperationCategoryList(operationId: String): List<OperationCategoryList> {
    return this.map {
        OperationCategoryList(operationId = operationId, categoryId = it.id)
    }
}

fun List<TransactionGoodsDatabaseEntity>.toTransactionGoodsList(transactionId: String): List<TransactionGoodsList> {
    return this.map {
        TransactionGoodsList(transactionId, it.id)
    }
}