package com.jk.financehelper.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

// при срабатывании уменьшается кол-во
data class Schedule(
    val id:String="",
    val startDate:LocalDateTime,
    val countLeft:Int,
    val periodMillis:Long
) {
}