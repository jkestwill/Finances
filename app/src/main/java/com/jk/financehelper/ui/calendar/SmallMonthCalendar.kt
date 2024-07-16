package com.jk.financehelper.ui.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jk.common_ui.composable.VerticalGrid
import com.jk.financehelper.ui.daysOfWeekArray
import com.jk.financehelper.ui.theme.Red
import com.kizitonwose.calendar.core.atStartOfMonth
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@NonRestartableComposable
@Composable
fun SmallMonthCalendar(modifier: Modifier, monthYear: YearMonth, footer: @Composable () -> Unit) {
    val currentMonth by remember {
        mutableStateOf(monthYear.atStartOfMonth())
    }
    val offset by remember(Unit) {
        mutableStateOf(currentMonth.dayOfWeek.value - 1)
    }
    val monthDaysList = remember(currentMonth) {
        derivedStateOf {
            (1..monthYear.lengthOfMonth() + offset).mapIndexed() { index, i ->
                if (offset >= index + 1) {
                    ""
                } else "${i - offset}"
            }
        }
    }
    Column(
        modifier = modifier
            .height(190.dp)

    ) {
        Text(
            text = monthYear.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
            fontSize = 10.sp
        )
        WeekHeader(modifier=Modifier.padding(0.dp))
        Months2(monthList = monthDaysList)
        footer()
    }
}

@Composable
fun Months2(modifier: Modifier = Modifier, monthList: State<List<String>>) {
    VerticalGrid(modifier = modifier, columns = 7) {
        repeat(monthList.value.size) {
            Text(
                text = monthList.value[it],
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                color=if((it+1)%7==0) Red else Color.Black
            )
        }

    }
}

@NonRestartableComposable
@Composable
fun WeekHeader(modifier: Modifier = Modifier) {
    val week = daysOfWeekArray
    Row(modifier = modifier) {
        repeat(7) {
            Text(
                modifier = Modifier
                    .weight(0.5f),
                text = "${week[it][0]}",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                maxLines = 1,
                color = if(it>5) Red else Color.Black
            )
        }
    }

}