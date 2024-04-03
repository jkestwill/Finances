package com.jk.financehelper.ui.calendar.data

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import java.time.Year


data class YearCalendarLayoutInfo(val info: LazyListLayoutInfo, private val years: (Int) -> Year): LazyListLayoutInfo by info{
    val visibleMonthsInfo: List<YearCalendarItemInfo>
        get() = visibleItemsInfo.map {
            YearCalendarItemInfo(it, years(it.index))
        }
}

data class YearCalendarItemInfo(val info: LazyListItemInfo, val year:Year):LazyListItemInfo by info
