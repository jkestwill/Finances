package com.jk.transaction_common_ui

import com.jk.category_common_ui.CategoryUI
import com.jk.goods_common_ui.models.GoodsUI
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI
import java.time.LocalDateTime
import java.time.LocalTime

data class TransactionUI(
    val id: String,
    val operation: OperationUI,
    val date: LocalDateTime,
    val type: TransactionTypeUI,
    val moneyAccountUI:MoneyAccountUI
) {
    class Builder() : BaseIdBuilder<TransactionUI>() {

        private var operation: OperationUI = OperationUI.Builder().build()
        private var date: LocalDateTime = LocalDateTime.now()
        private var type: TransactionTypeUI =
            TransactionTypeUI(id = "", name = TransactionTypeUI.Type.OFFLINE.value)
        val moneyAccountUI:MoneyAccountUI = MoneyAccountUI.Builder().build()

        fun setOperation(operationUI: OperationUI): Builder {
            this.operation = operationUI
            return this
        }

        fun setDate(date: LocalDateTime): Builder {
            this.date = date
            return this
        }

        fun setType(type: TransactionTypeUI): Builder {
            this.type = type
            return this
        }

        override fun build(): TransactionUI {
            return TransactionUI(id, operation, date, type,moneyAccountUI)
        }
    }
}

data class TransactionTypeUI(
    val id: String,
    val name: String
) {
    enum class Type(val value: String) {
        ONLINE("online"), OFFLINE("offline")
    }
}

data class OperationUI(
    val id: String,
    val name: String,
    val categoryList: List<CategoryUI>,
    val scheduleList: List<ScheduleUI>,
    val goodsList: List<GoodsUI>,
    val money: MoneyUI
) {
    fun builder(): OperationUI.Builder {
        return Builder()
            .setName(name)
            .setCategoryList(categoryList)
            .setScheduleList(scheduleList)
            .setGoodsList(goodsList)
            .setMoney(money)

    }

    class Builder() : BaseIdBuilder<OperationUI>() {

        private var name: String = ""
        private var categoryList: List<CategoryUI> = listOf()
        private var scheduleList: List<ScheduleUI> = listOf()
        private var goodsList: List<GoodsUI> = listOf()
        private var money: MoneyUI = MoneyUI(id = "", amount = 0.0, currency = CurrencyUI(id, name))

        fun setName(name: String): Builder {
            this.name = name
            return this
        }

        fun setCategoryList(categoryList: List<CategoryUI>): Builder {
            this.categoryList = categoryList
            return this
        }

        fun setScheduleList(scheduleList: List<ScheduleUI>): Builder {
            this.scheduleList = scheduleList
            return this
        }

        fun setGoodsList(goodsList: List<GoodsUI>): Builder {
            this.goodsList = goodsList
            return this
        }

        fun setMoney(moneyUI: MoneyUI): Builder {
            this.money = moneyUI
            return this
        }

        override fun build(): OperationUI {
            return OperationUI(id, name, categoryList, scheduleList, goodsList, money)
        }
    }

}

data class ScheduleUI(
    val id: String,
    val dateStart: LocalDateTime,
    val countLeft: Int,
    val repeatPeriodMillis: Long?,
    val time: LocalTime?,
    val day: Byte?,
    val week: Byte?,
    val month: Byte?
) {
    class Builder() : BaseIdBuilder<ScheduleUI>() {
        private var dateStart = LocalDateTime.now()
        private var countLeft: Int = 0
        private var repeatPeriodMillis: Long = 0L
        private var time: LocalTime? = null
        private var day: Byte? = null
        private var week: Byte? = null
        private var month: Byte? = null
        fun dateStart(dateStart: LocalDateTime): Builder {
            this.dateStart = dateStart
            return this
        }

        fun countLeft(countLeft: Int): Builder {
            this.countLeft = countLeft
            return this
        }

        fun repeatPeriodMillis(repeatPeriodMillis: Long): Builder {
            this.repeatPeriodMillis = repeatPeriodMillis
            return this
        }

        override fun build(): ScheduleUI {
            return ScheduleUI(id, dateStart, countLeft, repeatPeriodMillis, time, day, week, month)
        }

    }
}

abstract class BaseIdBuilder<T : Any> {
    var id: String = ""

    fun id(id: String): BaseIdBuilder<T> {
        this.id = id
        return this
    }

    abstract fun build(): T
}