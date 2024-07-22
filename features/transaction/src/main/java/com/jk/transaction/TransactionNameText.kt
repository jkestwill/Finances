package com.jk.transaction

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.composable.CharacterLimitTextField
import com.jk.shared_res.R

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
            Text(text = stringResource(R.string.name), style = FinanceHelperTheme.typography.h3)
        },
        textLimitConfig = TransactionNameTextLimit(
            minTextLengthErrorMessage = stringResource(
                R.string.minLengthError,
                3
            ), maxTextLengthErrorMessage = stringResource(R.string.maxLengthError, 100)
        ),
        onError = onError
    )
}

@Composable
fun CurrencyAmountText(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onError: (String?) -> Unit
) {
    CharacterLimitTextField(
        modifier = modifier, value = value, textLimitConfig = CurrencyAmountTextLimit(
            minTextLengthErrorMessage = stringResource(
                id = R.string.minLengthError, 1
            ), maxTextLengthErrorMessage = stringResource(id = R.string.maxLengthError, 5)
        ), onValueChange = onValueChange,
        onError = onError,
        placeHolder = {
            Text(text = stringResource(R.string.amount), style = FinanceHelperTheme.typography.h3)
        }
    )
}