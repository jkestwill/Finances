package com.jk.financehelper.ui.chart

import com.jk.financehelper.domain.model.Transaction
import java.time.LocalDateTime
import java.util.SortedMap
import java.util.TreeMap

object ChartInfo {

    fun combine(
        dateTypeList: List<LocalDateTime>,
        transactionList: List<Transaction>,
        chartData: ChartData
    ): SortedMap<Int, Double> {
        val newList: SortedMap<Int, Double> = TreeMap()
        for (i in dateTypeList.indices) {
            var sum = 0.0
            for (j in transactionList.indices) {
                sum += if (chartData.contains(transactionList[j].date, dateTypeList[i])) {
                    transactionList[j].operation.money.amount
                } else 0.0
            }
            newList[i] = sum
        }
        return newList
    }
}

