package com.jk.transaction_database.transaction.typeconverter

import androidx.room.TypeConverter
import java.time.LocalDate


object LocalDateTypeConverter {
     const val SECONDS_IN_DAY = 86_400

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(value * SECONDS_IN_DAY) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}