package com.jk.financehelper.ui.calendar.data

import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import java.time.LocalDate
import java.time.Year
import kotlin.math.max

@Composable
fun rememberYearState(startYear: Year, endYear: Year, firstVisibleYear: Year): YearCalendarState {
    return rememberSaveable(saver = YearCalendarState.Saver) {
        YearCalendarState(
            startYear = startYear,
            endYear = endYear,
            firstVisibleYear = firstVisibleYear
        )
    }
}

class YearCalendarState(
    startYear: Year,
    endYear: Year,
    firstVisibleYear: Year,
) : ScrollableState {

    val list = LazyListState(firstVisibleYear.value-startYear.value)

    val layoutInfo: YearCalendarLayoutInfo
        get() = YearCalendarLayoutInfo(list.layoutInfo) { index -> store[index] }

    // виден ли на экране год
    fun visibleItems(itemVisiblePercentThreshold: Float) = layoutInfo
            .visibleItemsInfo
            .filter {
                visibilityPercent(it) >= itemVisiblePercentThreshold
            }

    fun visibilityPercent(info: LazyListItemInfo): Float {

        val cutTop = max(0, layoutInfo.viewportStartOffset - info.offset)
        val cutBottom = max(0, info.offset + info.size - layoutInfo.viewportEndOffset)

        return max(0f, 100f - (cutTop + cutBottom) * 100f / info.size)
    }

    var startYear
        get() = _startYear
        set(value) {
            if (startYear != value) {
                _startYear = value
                yearUpdate()
            }
        }

    var _startYear by mutableStateOf(startYear)


    var yearIndexCountState by mutableStateOf(0)

    var endYear
        get() = _endYear
        set(value) {
            if (endYear != value) {
                _endYear = value
                yearUpdate()
            }
        }
    var _endYear by mutableStateOf(endYear)

    val firstVisibleYear: Year get() = _firstVisibleYear

    val _firstVisibleYear: Year by derivedStateOf {
        store[list.firstVisibleItemIndex]
    }

    val interactionSource: InteractionSource
        get() = list.interactionSource


    var store: MutableList<Year> = mutableStateListOf<Year>()

    init {
        yearUpdate()
    }

    private fun yearUpdate() {
        store.clear()
        checkYearRange(startYear, endYear)
        store = getYearList(startYear, endYear).toMutableList()
        yearIndexCountState = getYearIndicesCount(startYear, endYear)
    }


    fun getYearList(startYear: Year, endYear: Year): List<Year> {
        return (startYear.value..endYear.value).map { Year.of(it) }
    }

    private fun getYearIndicesCount(startYear: Year, endYear: Year): Int {
        return endYear.value - startYear.value
    }

    private fun checkYearRange(startYear: Year, endYear: Year) {
        if (startYear.value >= endYear.value) throw IllegalArgumentException("startYear is bigger or equals endYear")
    }

    override val isScrollInProgress: Boolean get() = list.isScrollInProgress

    override fun dispatchRawDelta(delta: Float): Float {

        return list.dispatchRawDelta(delta)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is YearCalendarState) return true
        return false
    }

    override suspend fun scroll(
        scrollPriority: MutatePriority,
        block: suspend ScrollScope.() -> Unit
    ) {
        list.scroll(scrollPriority, block)
    }

    suspend fun scrollTo(year: Year) {
        list.scrollToItem(getYearIndex(year) ?: return)
    }


    private fun getYearIndex(year: Year): Int? {
        return if (year !in startYear..endYear) {
            null
        } else {
            (year.value - startYear.value) - 1
        }
    }

    override fun hashCode(): Int {
        var result = list.hashCode()
        result = 31 * result + layoutInfo.hashCode()
        result = 31 * result + startYear.hashCode()
        result = 31 * result + _startYear.hashCode()
        result = 31 * result + yearIndexCountState
        result = 31 * result + endYear.hashCode()
        result = 31 * result + _endYear.hashCode()
        result = 31 * result + firstVisibleYear.hashCode()
        result = 31 * result + _firstVisibleYear.hashCode()
        result = 31 * result + interactionSource.hashCode()
        result = 31 * result + store.hashCode()
        result = 31 * result + isScrollInProgress.hashCode()
        return result
    }


    companion object {
        val Saver: Saver<YearCalendarState, Any> = listSaver(
            save = {
                listOf(
                    it.startYear,
                    it.endYear,
                    it.firstVisibleYear
                )
            },
            restore = {
                YearCalendarState(
                    startYear = it[0] as Year,
                    endYear = it[1] as Year,
                    firstVisibleYear = it[2] as Year
                )
            }
        )
    }
}