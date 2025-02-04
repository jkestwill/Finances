package com.jk.transaction_common_ui

import com.jk.transaction_common_data.Operation
import org.mapstruct.Mapper

@Mapper
interface OperationUIMapper {

    fun toOperationUI(operation: Operation):OperationUI

    fun toOperation(operationUI: OperationUI):Operation
}