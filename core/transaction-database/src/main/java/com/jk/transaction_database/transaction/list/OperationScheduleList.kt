package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.TransactionScheduleEntity

@Entity(
    "schedule_operation_list", primaryKeys = ["schedule_id", "operation_id"],
    foreignKeys = [ForeignKey(
        entity = TransactionScheduleEntity::class,
        parentColumns = ["id"],
        childColumns = ["schedule_id"],
    ),ForeignKey(
        entity = OperationEntity::class,
        parentColumns = ["id"],
        childColumns = ["operation_id"]
    )
    ]
)
data class OperationScheduleList(
    @ColumnInfo(name = "schedule_id")
    val scheduleId: String,
    @ColumnInfo(name = "operation_id")
    val operationId: String
) {
}