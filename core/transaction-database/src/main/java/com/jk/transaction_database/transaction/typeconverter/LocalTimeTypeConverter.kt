package com.jk.transaction_database.transaction.typeconverter

import androidx.room.TypeConverter
import java.time.LocalTime

object LocalTimeTypeConverter {

    @TypeConverter
    fun fromTimestampToLocalTime(string: String): LocalTime {
        val splited = string.split(" ")
        return LocalTime.of(splited[0].toInt(), splited[1].toInt())
    }

    @TypeConverter
    fun fromLocalTimeToTimestamp(localTime: LocalTime): String {
        return ("${localTime.hour} ${localTime.minute}")
    }
}