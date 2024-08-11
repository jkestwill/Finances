package com.jk.transaction_common_ui

import android.os.Parcelable
import com.jk.category_common_ui.CategoryUI
import com.jk.goods_common_ui.GoodsUI
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

data class TransactionUI(
    val id: String,
    val operation: OperationUI,
    val date: LocalDateTime,
    val type: TransactionTypeUI
) {
    class Builder() {
        private var id: String = ""
        private var operation: OperationUI = OperationUI.Builder().build()
        private var date: LocalDateTime = LocalDateTime.now()
        private var type: TransactionTypeUI = TransactionTypeUI(id = "", name = TransactionTypeUI.Type.OFFLINE.value)

        fun setId(id: String) {
            this.id = id
        }

        fun setOperation(operationUI: OperationUI) {
            this.operation = operationUI
        }

        fun setDate(date: LocalDateTime) {
            this.date = date
        }

        fun setType(type: TransactionTypeUI) {
            this.type = type
        }

        fun build(): TransactionUI {
            return TransactionUI(id, operation, date, type)
        }
    }
}

data class TransactionTypeUI(
    val id: String,
    val name: String
){
    enum class Type(val value:String){
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

    class Builder() {

        private var id: String = ""
        private var name: String = ""
        private var categoryList: List<CategoryUI> = listOf()
        private var scheduleList: List<ScheduleUI> = listOf()
        private var goodsList: List<GoodsUI> = listOf()
        private var money: MoneyUI = MoneyUI(id = "", amount = 0.0, currency = CurrencyUI(id, name))

        fun setId(id: String): Builder {
            this.id = id
            return this
        }

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

        fun build(): OperationUI {
            return OperationUI(id, name, categoryList, scheduleList, goodsList, money)
        }
    }

}

data class ScheduleUI(
    val id: String,
    val dateStart: LocalDateTime,
    val countLeft: Int,
    val repeatPeriodMillis: Long
)

