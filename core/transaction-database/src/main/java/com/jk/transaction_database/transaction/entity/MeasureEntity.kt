package com.jk.transaction_database.transaction.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity("measure", indices = [Index("name", unique = true)])
data class MeasureEntity(
    @PrimaryKey
    val id:String,
    @ColumnInfo(name="name", typeAffinity = ColumnInfo.TEXT)
    val name:String
)