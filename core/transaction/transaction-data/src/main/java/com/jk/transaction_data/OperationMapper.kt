package com.jk.transaction_data

import com.jk.transaction_common_data.Operation
import com.jk.transaction_database.transaction.relations.OperationGoodsRelation
import com.jk.transaction_database.transaction.relations.OperationTransactionRelation
import org.mapstruct.Mapper

@Mapper
interface OperationMapper {

    fun toOperation(operationGoodsRelations: OperationGoodsRelation):Operation

    fun toOperationRelation(operation: Operation):OperationTransactionRelation
}