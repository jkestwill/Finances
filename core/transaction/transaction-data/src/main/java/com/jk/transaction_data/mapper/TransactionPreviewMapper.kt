package com.jk.transaction_data.mapper

import com.jk.transaction_common_data.TransactionPreview
import com.jk.transaction_database.transaction.preview.TransactionPreviewRelation
import org.mapstruct.Mapper

@Mapper
interface TransactionPreviewMapper {


    fun toPreview(transactionPreview: TransactionPreviewRelation):TransactionPreview

    fun toPreviewEntity(transactionPreview:TransactionPreview):TransactionPreviewRelation
}