package com.jk.goods_common_ui

import android.os.Parcelable
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI
import kotlinx.parcelize.Parcelize


@Parcelize
data class GoodsUI(
    val id: String,
    val name: String,
    val amount: Int,
    val specifications: List<SpecificationsUI>,
    val cost: MoneyUI
) : Parcelable {

    fun toBuilder(): Builder {
        return Builder()
            .id(id)
            .name(name)
            .amount(amount)
            .specifications(specifications)
            .cost(cost)
    }

    class Builder() {

        private var id: String = ""
        private var name: String = ""
        private var amount = 0
        private var specifications = listOf<SpecificationsUI>()
        private var cost = MoneyUI(id = "", amount = 0.0, currency = CurrencyUI(id = "", name = ""))

        fun id(id: String): Builder {
            this.id = id
            return this
        }

        fun name(name: String): Builder {
            this.name = name
            return this
        }

        fun amount(amount: Int): Builder {
            this.amount = amount
            return this
        }


        fun specifications(specifications: List<SpecificationsUI>): Builder {
            this.specifications = specifications
            return this
        }

        fun cost(cost: MoneyUI): Builder {
            this.cost = cost
            return this
        }

        fun build(): GoodsUI {
            return GoodsUI(id, name, amount = amount, specifications, cost = cost)
        }

        override fun toString(): String {
            return build().toString()
        }
    }

}

