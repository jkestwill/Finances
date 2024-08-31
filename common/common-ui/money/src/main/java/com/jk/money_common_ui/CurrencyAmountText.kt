package com.jk.money_common_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.composable.CharacterLimitTextField
import com.jk.shared_res.R
@Composable
fun CurrencyAmountText(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onExpand: (() -> Unit)? = null,
    onError: (String?) -> Unit,
    onDone: KeyboardActionScope.() -> Unit = {}
) {
    CharacterLimitTextField(
        modifier = modifier
            .background(
                FinanceHelperTheme.colors.defaultButtonColor.copy(0.5f),
                shape = FinanceHelperTheme.shape.shapeRoundMedium
            ),
        value = value,
        textLimitConfig = CurrencyAmountTextLimit(
            minTextLengthErrorMessage = stringResource(id = R.string.minLengthError, 1),
            maxTextLengthErrorMessage = stringResource(id = R.string.maxLengthError, 10)
        ),
        onValueChange = onValueChange,
        onError = onError,
        maxLines = 1,
        maxLengthPostfixVisibility = false,
        placeHolder = {
            Text(
                text = stringResource(R.string.amount),
                style = FinanceHelperTheme.typography.h3,
                color = Color.Black.copy(0.5f)
            )
        },
        onClick = onExpand,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = onDone),
        textStyle = FinanceHelperTheme.typography.h3
    )
}