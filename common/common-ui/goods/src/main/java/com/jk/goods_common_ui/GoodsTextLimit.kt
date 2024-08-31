package com.jk.goods_common_ui

import com.jk.common_ui.composable.Limit
import com.jk.common_ui.composable.TextError
import com.jk.common_ui.composable.TextLimit
import com.jk.common_ui.composable.TextLimitConfig

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
            } else it
        }
    ),
    error = TextError(
        minTextLengthError = minTextLengthErrorMessage,
        maxTextLengthError = maxTextLengthErrorMessage
    ),
)

class GoodsNameTextLimit(
    minTextLengthErrorMessage: String,
    maxTextLengthErrorMessage: String
) : TextLimitConfig(
    TextLimit(Limit(1, true), Limit(100, false)),
    error = TextError(minTextLengthError = minTextLengthErrorMessage, maxTextLengthErrorMessage)
)