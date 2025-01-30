package com.jk.transaction_database.transaction.entity

import com.jk.transaction_database.transaction.list.OperationCategoryList
import com.jk.transaction_database.transaction.list.OperationGoodsListEntity


fun List<CategoryEntity>.toOperationCategoryList(operationId: String): List<OperationCategoryList> {
    return this.map {
        OperationCategoryList(operationId = operationId, categoryId = it.id)
    }
}

fun List<GoodsEntity>.toTransactionGoodsList(transactionId: String): List<OperationGoodsListEntity> {
    return this.map {
        OperationGoodsListEntity(transactionId, it.id)
    }
}