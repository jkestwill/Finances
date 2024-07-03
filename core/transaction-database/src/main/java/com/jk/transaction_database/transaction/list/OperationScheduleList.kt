package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.TransactionScheduleDatabaseEntity

@Entity(
    "schedule_operation_list", primaryKeys = ["schedule_id", "operation_id"],
    foreignKeys = [ForeignKey(
        entity = TransactionScheduleDatabaseEntity::class,
        parentColumns = ["id"],
        childColumns = ["schedule_id"],
        onDelete = ForeignKey.CASCADE
    ),ForeignKey(
        entity = OperationEntity::class,
        parentColumns = ["id"],
        childColumns = ["operation_id"],
        onDelete = ForeignKey.CASCADE)

    ]
)
data class OperationScheduleList(
    @ColumnInfo(name = "schedule_id")
    val scheduleId: String,
    @ColumnInfo(name = "operation_id")
    val operationId: String
) {
}