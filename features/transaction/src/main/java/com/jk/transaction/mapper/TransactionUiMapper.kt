package com.jk.transaction.mapper

import com.jk.transaction_common_data.Transaction
import com.jk.transaction_common_data.TransactionPreview
import com.jk.transaction_common_ui.TransactionPreviewUI
import com.jk.transaction_common_ui.TransactionUI
import org.mapstruct.Mapper

@Mapper
interface TransactionUiMapper {
    fun toTransacionUI(transaction:Transaction):TransactionUI

    fun toTransaction(transactionUI: TransactionUI):Transaction

    fun toTransactionPreview(transactionPreviewUI: TransactionPreviewUI):TransactionPreview

    fun toTransactionPreviewUI(transactionPreview: TransactionPreview):TransactionPreviewUI
}