package com.jk.transaction

import android.content.Context
import com.jk.common_ui.StringResourceExceptionHandler
import com.jk.common_ui.UIException
import com.jk.goods_common_ui.exceptions.GoodsNameLengthException
import com.jk.money_common_ui.exceptions.CurrencyConstraints
import com.jk.money_common_ui.exceptions.CurrencyNameLengthException
import com.jk.money_common_ui.exceptions.MoneyAmountException
import com.jk.money_common_ui.exceptions.MoneyConstraints
import com.jk.shared_res.R

class TransactionStringResourceExceptionHandler(private val context: Context) :
    StringResourceExceptionHandler<UIException> {

    override fun handle(exception: UIException): String {
        return when (exception) {
            is GoodsNameLengthException -> {
                context.getString(R.string.goods_empty_name_error)
            }

            is CurrencyNameLengthException -> context.getString(
                R.string.currency_name_length_error,
                CurrencyConstraints.MIN_CURRENCY_NAME_LENGTH
            )

            is MoneyAmountException -> context.getString(
                R.string.money_min_value,
                MoneyConstraints.AMOUNT_MIN
            )

            else -> context.getString(R.string.error)
        }
    }
}

fun  TransactionStringResourceExceptionHandler.getStringErrorFromResource(
    tryBlock: () -> Unit,
    catchBlock: (String) -> Unit = {}
) {
    try {
        tryBlock()
    } catch (e: UIException) {
        catchBlock(handle(e))
    }
}

suspend fun TransactionStringResourceExceptionHandler.suspendGetStringErrorFromResource(
    tryBlock: suspend () -> Unit,
    catchBlock: (String) -> Unit = {}
) {
    try {
        tryBlock()
    } catch (e: UIException) {
        catchBlock(handle(e))
    }
}

