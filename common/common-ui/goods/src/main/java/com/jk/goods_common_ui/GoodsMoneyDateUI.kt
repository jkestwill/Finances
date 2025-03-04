package com.jk.goods_common_ui

import android.os.Parcelable
import com.jk.common_data.Selectable
import com.jk.common_goods_data.Goods
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyDateUI
import com.jk.money_common_ui.MoneyUI
import kotlinx.parcelize.Parcelize


@Parcelize
data class GoodsMoneyDateUI(
    val id: String,
    val name: String,
    val amount: Int,
    val specifications: List<SpecificationsUI>,
    val cost:MoneyUI
) : Parcelable, Selectable {

    override val value: String
        get() = id

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
        private var cost = MoneyUI("",0.0, currency = CurrencyUI("",""))

        constructor(goods: GoodsMoneyDateUI) : this() {
            id = goods.id
            name = goods.name
            amount = goods.amount
            specifications = goods.specifications
            cost = goods.cost
        }

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

        fun build(): GoodsMoneyDateUI {
            return GoodsMoneyDateUI(id, name, amount = amount, specifications, cost = cost)
        }

        override fun hashCode(): Int {
            return id.hashCode() + name.hashCode() + amount.hashCode() + specifications.hashCode() + cost.hashCode()

        }

        override fun equals(other: Any?): Boolean {
            if (other == null) return false
            if (other is GoodsMoneyDateUI.Builder) {
                return if (other.id.isEmpty())
                    other.name == name && other.amount == amount && other.specifications == specifications && other.cost == cost
                else other.id == id
            }
            if (other is Goods) {
                return other == build()
            }
            return false
        }

        override fun toString(): String {
            return build().toString()
        }
    }

}

