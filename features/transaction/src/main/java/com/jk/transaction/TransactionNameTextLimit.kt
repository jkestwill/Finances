package com.jk.transaction

import com.jk.common_ui.composable.Limit
import com.jk.common_ui.composable.TextError
import com.jk.common_ui.composable.TextLimit
import com.jk.common_ui.composable.TextLimitConfig

class TransactionNameTextLimit(
    minTextLengthErrorMessage: String,
    maxTextLengthErrorMessage: String
) : TextLimitConfig(
    TextLimit(Limit(3, true), Limit(100, false)),
    error = TextError(minTextLengthError = minTextLengthErrorMessage, maxTextLengthErrorMessage)
)

class CurrencyAmountTextLimit(
    minTextLengthErrorMessage: String,
    maxTextLengthErrorMessage: String
) : TextLimitConfig(
    textLimit = TextLimit(
        Limit(1, true),
        Limit(10, false),
        allowedSpecialCharacters = Limit(listOf('.'), false),
        regexPattern = Limit("[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)", false),
        onValueChange = {
            (if (it.length > 1) {
                if (it.startsWith("00"))
                    it[0].toString()
                else
                    it.trimStart('0')
            }
            else it).format("%.2f")
        }
    ),
    error = TextError(minTextLengthError = minTextLengthErrorMessage, maxTextLengthErrorMessage))

class GoodsCountTextLimit(
    minTextLengthErrorMessage: String,
    maxTextLengthErrorMessage: String
) : TextLimitConfig(
    textLimit = TextLimit(
        minLength = Limit(1, true),
        maxLength = Limit(7, false),
        regexPattern = Limit("\\d+$", false),
        onValueChange = {
            if (it.length > 1) {
                if (it.startsWith("00"))
                    it[0].toString()
                else
                    it.trimStart('0')
            }
            else it
        }
    ),
    error = TextError(
        minTextLengthError = minTextLengthErrorMessage,
        maxTextLengthError = maxTextLengthErrorMessage
    ),

    )