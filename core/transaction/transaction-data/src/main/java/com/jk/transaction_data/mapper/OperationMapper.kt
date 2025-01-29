package com.jk.transaction_data.mapper

import com.jk.transaction_common_data.Operation
import com.jk.transaction_database.transaction.relations.OperationAndGoodsListRelation
import com.jk.transaction_database.transaction.relations.TransactionxCategoriesxTypexGoods
import org.mapstruct.Mapper

@Mapper
interface OperationMapper {

    fun toOperation(operationGoodsRelations: OperationAndGoodsListRelation):Operation

    fun toOperationRelation(operation: Operation):TransactionxCategoriesxTypexGoods
}