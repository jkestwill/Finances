package com.jk.transaction

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.composable.CharacterLimitTextField
import com.jk.common_ui.composable.Limit
import com.jk.common_ui.composable.TextError
import com.jk.common_ui.composable.TextLimit
import com.jk.common_ui.composable.TextLimitConfig

@Composable
fun TransactionNameText(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onError: (String?) -> Unit
) {
    CharacterLimitTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeHolder = {
            Text(text = "Name", style = FinanceHelperTheme.typography.h3)
        },
        textLimitConfig = TextLimitConfig(
            TextLimit(Limit(3, true), Limit(100, false)),
            error = TextError(minTextLengthError = "Min text length must be 3")
        ),
        onError = onError
    )
}