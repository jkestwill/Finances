package com.jk.transaction_database.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "language")
data class LanguageEntity(
    @PrimaryKey
    val id:String,
    @ColumnInfo("lang_name")
    val langName:String,
    @ColumnInfo("short_lang_name")
    val langShortName:String,
)