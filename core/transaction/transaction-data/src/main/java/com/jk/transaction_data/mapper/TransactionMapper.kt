package com.jk.transaction_data.mapper

import com.jk.transaction_common_data.Transaction
import com.jk.transaction_database.transaction.relations.TransactionxCategoriesxTypexGoods
import org.mapstruct.Mapper

@Mapper
interface TransactionMapper {

    fun toTransaction(transactionxCategoriesxTypexGoods: TransactionxCategoriesxTypexGoods):Transaction

    fun toTransactionxCategoryXTypeXGoods(transaction: Transaction):TransactionxCategoriesxTypexGoods
}