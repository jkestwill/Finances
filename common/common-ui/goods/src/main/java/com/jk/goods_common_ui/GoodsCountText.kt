package com.jk.goods_common_ui

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.composable.CharacterLimitTextField
import com.jk.shared_res.R

@Composable
fun GoodsCountText(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onClick: (() -> Unit)? = null,
    onError: (String?) -> Unit
) {
    CharacterLimitTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        onClick = onClick,
        onError = onError,
        textLimitConfig = GoodsCountTextLimit(
            minTextLengthErrorMessage = stringResource(id = R.string.minLengthError, 1),
            maxTextLengthErrorMessage = stringResource(
                id = R.string.maxLengthError, 7
            )
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        maxLengthPostfixVisibility = false,
        textStyle = FinanceHelperTheme.typography.h3
    )
}