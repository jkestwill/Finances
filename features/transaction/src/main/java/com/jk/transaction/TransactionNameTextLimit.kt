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
        regexPattern = Limit("[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)",false)
    ),
    error = TextError(minTextLengthError = minTextLengthErrorMessage, maxTextLengthErrorMessage),
    )