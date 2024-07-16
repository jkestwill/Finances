package com.jk.financehelper.ui.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jk.common_ui.Celadon
import com.jk.financehelper.ui.calendar.data.YearCalendarState
import com.jk.financehelper.ui.calendar.data.pagedFlingBehavior
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth


@Composable
fun YearCalendar(modifier: Modifier, currentDate: State<LocalDate>, yearState:YearCalendarState, onMonthClick:(YearMonth)->Unit,footer:@Composable ()->Unit) {

    val selectedIndex = remember() {
        mutableIntStateOf(currentDate.value.monthValue)
    }
    YearPager(modifier = modifier, state = yearState) { year ->
        Column {
            Text(text = "${year.value}",)
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth(),
                columns = GridCells.Fixed(3)
            ) {
                items(12, key = { it }, contentType = { it }) {
                    SmallMonthCalendar(
                        modifier = Modifier
                            .padding(2.dp)
                            .drawBehind {
                                if(selectedIndex.intValue==it){
                                    val thickness = 10.dp.toPx()
                                    this.layoutDirection.ordinal
                                    drawLine(color= Celadon,start= Offset(x=0f,y=0f), end = Offset(x=size.width,y=0f), strokeWidth = thickness)
                                }else drawRect(Color.Transparent)
                            }
                            .selectable(
                                selected = selectedIndex.intValue == it
                            ) {
                                selectedIndex.intValue = if (selectedIndex.intValue != it) {
                                    onMonthClick(YearMonth.of(year.value,it+1))
                                    it
                                } else -1
                            },
                        monthYear = YearMonth.of(year.value, it + 1),
                        footer=footer
                    )
                }
            }
        }

    }
}


@Composable
private fun YearPager(
    modifier: Modifier,
    state: YearCalendarState,
    content: @Composable (year: Year) -> Unit
) {
    LazyRow(
        modifier = modifier,
        state = state.list,
        flingBehavior = pagedFlingBehavior(state = state.list)
    ) {
        items(
            count = state.yearIndexCountState,
            key = { it },
            contentType = { state.store[it] }) { offset ->
            Box(modifier = Modifier.fillParentMaxWidth()) {
                content(state.store[offset])
            }
        }
    }
}
