package com.jk.financehelper.ui.calendar.somnitelno

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jk.financehelper.ui.custom.AutoSizeText
import com.jk.financehelper.ui.daysOfWeekArray
import java.time.LocalDate
import java.time.YearMonth

// показывает текущий месяц с концом прошлого месяца и началом следующего
@Composable
fun MonthItem(currentMonth: Int, year: Int, onDayClick: (LocalDate) -> Unit) {
    val month = YearMonth.of(year, currentMonth)
    val currentLocalDate by remember { mutableStateOf(LocalDate.of(year, currentMonth, 1)) }
    val daysInMonth = month.lengthOfMonth()
    val firstDayOfWeek = currentLocalDate.dayOfWeek.value - 1
    val daysOfWeekList = daysOfWeekArray.toMutableList()
    val prevMonthDayCount = month.minusMonths(1).lengthOfMonth()
    var daysShiftCount by remember { mutableStateOf(firstDayOfWeek) }
    var dayOnClick by remember {
        mutableStateOf(currentLocalDate)
    }

    Column {
        LazyVerticalGrid(columns = GridCells.Fixed(7)) {
            items(daysOfWeekList) {
                AutoSizeText(
                    text = "${it[0]}",
                    color = Color.Red,
                    alignment = Alignment.Center,
                    minTextSize = 12.sp,
                    maxTextSize = 16.sp
                )
            }
            items(35) {
                var color by remember { mutableStateOf(Color.Black) }
                var date by remember {
                    mutableStateOf(currentLocalDate)
                }
                val day = if (it < daysShiftCount) {
                    date = dayOnClick.minusMonths(1)
                    color = Color.Gray
                    date = date.withDayOfMonth((prevMonthDayCount + 1) - daysShiftCount + it)
                    date.dayOfMonth

                } else if (it + 1 - daysShiftCount > daysInMonth) {
                    color = Color.Gray
                    date = dayOnClick.plusMonths(1)
                    date = date.withDayOfMonth(((it + 1) - daysShiftCount) % daysInMonth)
                    date.dayOfMonth

                } else {
                    date = date.withDayOfMonth(it - daysShiftCount + 1)
                    date.dayOfMonth
                }
                Box(modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clickable {
                        onDayClick(date)
                    }) {
                    AutoSizeText(
                        modifier = Modifier.align(Alignment.Center),
                        text = (day).toString(),
                        color = color,
                        alignment = Alignment.Center,
                        minTextSize = 12.sp,
                        maxTextSize = 16.sp
                    )
                }

            }
        }
    }
}
