package com.jk.shared_res


import android.content.Context
import androidx.core.content.ContextCompat.getString
import com.jk.common_data.CurrencyConstraints
import com.jk.common_data.FinanceHelperException
import com.jk.common_data.exceptions.CurrencyNameLengthException
import com.jk.common_data.exceptions.GoodsNameLengthException
import com.jk.common_data.exceptions.MoneyAmountException
import com.jk.common_data.exceptions.MoneyConstraints
import com.jk.common_data.exceptions.OperationNameLengthException
import com.jk.common_data.exceptions.TransactionDateAfterNowException
import com.jk.common_data.exceptions.TransactionTypeEmptyException

class BusinessExceptionsHandler(private val context: Context) {

    fun handle(exception: FinanceHelperException.BusinessLogicException): String {
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
            is OperationNameLengthException-> context.getString(R.string.operation_empty_name_error)

            is TransactionDateAfterNowException-> context.getString(R.string.transaction_date_after_error)

            is TransactionTypeEmptyException -> context.getString(R.string.transaction_type_empty_error)

            else -> getString(context, R.string.error)
        }
    }
}