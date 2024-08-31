package com.jk.money_common_ui

import com.jk.common_ui.composable.Limit
import com.jk.common_ui.composable.TextError
import com.jk.common_ui.composable.TextLimit
import com.jk.common_ui.composable.TextLimitConfig

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
            } else it).format("%.2f")
        }
    ),
    error = TextError(minTextLengthError = minTextLengthErrorMessage, maxTextLengthErrorMessage))