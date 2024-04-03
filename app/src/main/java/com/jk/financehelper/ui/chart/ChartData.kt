package com.jk.financehelper.ui.chart

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.core.atStartOfMonth
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class ChartDataStyle(
    val textSize: TextUnit = 16.sp,
    val textColor: Color = Color.Black,
    val verticalAxisItemCount: Int = 3,
    val spacing: Dp = 20.dp
)

sealed class ChartData(
    val chartDataStyle: ChartDataStyle,
    val locale: Locale = Locale.ENGLISH,
    val dateStart: LocalDateTime,
    val dateEnd: LocalDateTime
) {
    abstract fun getStringItems(yearMonth: YearMonth): List<String>

    abstract fun getDateItems(date: LocalDateTime): List<LocalDateTime>

    abstract fun contains(actualDate: LocalDateTime, checkDate: LocalDateTime): Boolean

    class MonthChartData(
        dateYearMonth: YearMonth
    ) : ChartData(
        chartDataStyle = ChartDataStyle(
            textSize = 15.sp,
            textColor = Color.Black,
            verticalAxisItemCount = 3
        ),
        locale = Locale("en"),
        dateStart = dateYearMonth.atStartOfMonth().atStartOfDay(),
        dateEnd = dateYearMonth.atEndOfMonth().atTime(23, 59, 59)
    ) {
        override fun getStringItems(yearMonth: YearMonth): List<String> {
            return List(yearMonth.lengthOfMonth()) {
                (it + 1).toString()
            }
        }

        override fun getDateItems(date: LocalDateTime): List<LocalDateTime> {
            val yearMonth = YearMonth.of(date.year, date.month)
            return List(yearMonth.lengthOfMonth()) {
                LocalDateTime.of(yearMonth.year, yearMonth.month, it + 1, 0, 0)

            }
        }

        override fun contains(actualDate: LocalDateTime, checkDate: LocalDateTime): Boolean {
            return checkDate.year == actualDate.year &&
                    checkDate.month == actualDate.month &&
                    checkDate.dayOfMonth == actualDate.dayOfMonth
        }
    }

    class YearChartData(year: Int) : ChartData(
        chartDataStyle = ChartDataStyle(
            textSize = 15.sp,
            textColor = Color.Black,
            verticalAxisItemCount = 3
        ),
        locale = Locale("en"),
        dateStart = LocalDateTime.of(year, 1, 1, 0, 0),
        dateEnd = LocalDateTime.of(year, 12, 1, 0, 0),
    ) {
        override fun getStringItems(yearMonth: YearMonth): List<String> {
            return List(12) {
                Month.of(it + 1).getDisplayName(TextStyle.SHORT, locale)
            }
        }

        override fun getDateItems(date: LocalDateTime): List<LocalDateTime> {
            val yearMonth = YearMonth.of(date.year, date.month)
            return List(12) {
                LocalDateTime.of(yearMonth.year, it + 1, 1, 0, 0)
            }
        }

        override fun contains(actualDate: LocalDateTime, checkDate: LocalDateTime): Boolean {
            return checkDate.year == actualDate.year && checkDate.month == actualDate.month
        }
    }

    class DayChartData(dateStart: LocalDate) : ChartData(
        chartDataStyle = ChartDataStyle(
            textSize = 15.sp,
            textColor = Color.Black,
            verticalAxisItemCount = 3
        ),
        locale = Locale("en"),
        dateStart = dateStart.atStartOfDay(),
        dateEnd = dateStart.atTime(23, 59, 59, 59)
    ) {
        override fun getStringItems(yearMonth: YearMonth): List<String> {
            return List(24) {
                "${it}:00"
            }
        }

        override fun getDateItems(date: LocalDateTime): List<LocalDateTime> {
            return List(24) {
                LocalDateTime.of(date.year, date.month, date.dayOfMonth, it, 0)
            }
        }

        override fun contains(actualDate: LocalDateTime, checkDate: LocalDateTime): Boolean {
            return checkDate.year == actualDate.year &&
                    checkDate.month == actualDate.month &&
                    checkDate.dayOfMonth == actualDate.dayOfMonth &&
                    checkDate.hour == actualDate.hour
        }
    }

}



