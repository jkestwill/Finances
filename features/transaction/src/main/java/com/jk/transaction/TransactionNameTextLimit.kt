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



