package com.jk.goods_common_ui.models

import android.os.Parcelable
import com.jk.common_data.Selectable
import com.jk.common_goods_data.Goods
import com.jk.goods_common_ui.SpecificationsUI
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI
import kotlinx.parcelize.Parcelize


@Parcelize
data class GoodsUI(
    val id: String,
    val name: String,
    val specifications: List<SpecificationsUI>?,
    val cost:MoneyUI
) : Parcelable, Selectable {

    override val value: String
        get() = id

    fun toBuilder(): Builder {
        return Builder()
            .id(id)
            .name(name)
            .specifications(specifications)
            .cost(cost)
    }

    class Builder() {
        private var id: String = ""
        private var name: String = ""
        private var specifications:List<SpecificationsUI>? = null
        private var cost = MoneyUI("",0.0, currency = CurrencyUI("",""))

        constructor(goods: GoodsUI) : this() {
            id = goods.id
            name = goods.name
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

        fun specifications(specifications: List<SpecificationsUI>?): Builder {
            this.specifications = specifications
            return this
        }

        fun cost(cost: MoneyUI): Builder {
            this.cost = cost
            return this
        }

        fun build(): GoodsUI {
            return GoodsUI(id, name, specifications, cost = cost)
        }

        override fun hashCode(): Int {
            return id.hashCode() + name.hashCode()  + specifications.hashCode() + cost.hashCode()

        }

        override fun equals(other: Any?): Boolean {
            if (other == null) return false
            if (other is Builder) {
                return if (other.id.isEmpty())
                    other.name == name && other.specifications == specifications && other.cost == cost
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

