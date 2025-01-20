package com.jk.financehelper.ui.calendar

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jk.common_ui.SeaGreen
import com.jk.financehelper.ui.chart.ChartData
import com.jk.financehelper.ui.chart.VicoGraph
import com.jk.common_ui.SwipeableText
import com.jk.common_ui.composable.AutoSizeText
import com.jk.financehelper.R
import com.jk.financehelper.ui.monthsArray
import com.jk.financehelper.utils.DataUtils
import com.jk.transaction_common_ui.TransactionUI
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.yearMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale


private const val TAG = "CalendarView"


//@Composable
//fun CalendarPager(
//    currentDate: LocalDate,
//    //viewModel: HomeViewModel,
//    onDateClick: (LocalDate) -> Unit
//) {
//    val state = rememberCalendarState(
//        startMonth = YearMonth.of(2024, Month.JANUARY),
//        endMonth = YearMonth.of(2024, Month.MAY),
//        firstVisibleMonth = YearMonth.of(2025, currentDate.month + 1),
//        outDateStyle = OutDateStyle.EndOfRow
//    )
//    var selectedDate by remember {
//        mutableStateOf(currentDate)
//    }
//    val transactionList: List<TransactionUI> by viewModel.transactionsFlow.collectAsState(initial = listOf())
//
//    val dateStyleList: List<ChartData> = remember() {
//        mutableListOf(
//            ChartData.YearChartData(selectedDate.year),
//            ChartData.MonthChartData(selectedDate.yearMonth),
//            ChartData.DayChartData(selectedDate)
//        )
//    }
//
//    val dateTypeCount by remember {
//        mutableStateOf(0)
//    }
//    val currentDateStyle by remember() {
//        mutableStateOf(ChartData.YearChartData(selectedDate.year))
//    }
//
//
//    val scope = rememberCoroutineScope()
//    Column(modifier = Modifier.fillMaxWidth()) {
//        SwipeableText(
//            text = monthsArray[state.firstVisibleMonth.yearMonth.month.value - 1],
//            onTextCLick = {
//                dateStyleList[dateTypeCount % (dateStyleList.size - 1)]
//            },
//            onBack ={  scope.launch {
//                state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.minusMonths(1))
//             }
//            },
//            onForward = {
//                scope.launch {
//                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.plusMonths(1))
//                }
//            }, painterBack = painterResource(id = R.drawable.arrow_l) , painterForward = painterResource(
//                id = R.drawable.arrow_r
//            ))
//
//
//
//        HorizontalCalendar(modifier = Modifier.fillMaxWidth(), state = state, dayContent = {
//            val color by remember(it.position) {
//                mutableStateOf(
//                    if (it.position == DayPosition.MonthDate) {
//                        Color.Black
//                    } else {
//                        Color.Gray
//                    }
//                )
//            }
//
//            val borderColor by remember(selectedDate) {
//                mutableStateOf(
//                    if (selectedDate == it.date) {
//                        SeaGreen
//                    } else {
//                        Color.Transparent
//                    }
//                )
//            }
//            Box(modifier = Modifier
//                .clip(RoundedCornerShape(10))
//                .clickable {
//                    selectedDate = it.date
//                    onDateClick(it.date)
//                }
//                .border(
//                    4.dp,
//                    color = borderColor
//                )
//                .fillMaxWidth()) {
//                AutoSizeText(
//                    modifier = Modifier
//                        .align(Alignment.Center)
//                        .padding(10.dp),
//                    text = it.date.dayOfMonth.toString(),
//                    color = color,
//                    minTextSize = 10.sp,
//                    maxTextSize = 15.sp,
//                    alignment = Alignment.Center
//                )
//            }
//        }, monthHeader = { month ->
//            val daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }
//            DaysOfWeekTitle(daysOfWeek = daysOfWeek)
//        }, monthBody = { calendarMonth: CalendarMonth, content: @Composable () -> Unit ->
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                content()
//            }
//        }, monthContainer = { _, container ->
//            val configuration = LocalConfiguration.current
//            val screenWidth = configuration.screenWidthDp.dp
//            Box(
//                modifier = Modifier
//                    .width(screenWidth)
//                    .padding(8.dp)
//                    .clip(shape = RoundedCornerShape(8.dp))
//                    .border(
//                        color = Color.Black,
//                        width = 2.dp,
//                        shape = RoundedCornerShape(8.dp)
//                    )
//            ) {
//                container()
//            }
//        }, monthFooter = {
//            VicoGraph(
//                modifier = Modifier.height(200.dp),
//                transactionList = DataUtils.getBarChartData(),
//                chartDataType = currentDateStyle
//            )
//
//        })
//    }
//}
@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            AutoSizeText(
                modifier = Modifier.weight(1f),
                alignment = Alignment.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                minTextSize = 8.sp,
                maxTextSize = 16.sp,
                maxLines = 1
            )
        }
    }
}