package com.jk.transaction

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.composable.CharacterLimitTextField
import com.jk.common_ui.composable.KeyboardState
import com.jk.common_ui.composable.keyboardAsState
import com.jk.shared_res.R

@Composable
fun TransactionNameText(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onError: (String?) -> Unit
) {

    CharacterLimitTextField(
        modifier = modifier
            .background(
                FinanceHelperTheme.colors.defaultButtonColor.copy(0.5f),
                shape = FinanceHelperTheme.shape.shapeRoundMedium
            ),
        value = value,
        onValueChange = onValueChange,
        placeHolder = {
            Text(
                text = stringResource(R.string.name),
                style = FinanceHelperTheme.typography.h3,
                color = Color.Black.copy(0.5f)
            )
        },
        maxLengthPostfixVisibility = true,
        maxLines = 1,
        textLimitConfig = TransactionNameTextLimit(
            minTextLengthErrorMessage = stringResource(
                R.string.minLengthError,
                3
            ), maxTextLengthErrorMessage = stringResource(R.string.maxLengthError, 100)
        ),
        onError = onError,
        textStyle = FinanceHelperTheme.typography.h3
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

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        textStyle = FinanceHelperTheme.typography.h3
    )
}

@Composable
fun GoodsNameText(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onError: (String?) -> Unit
) {
    CharacterLimitTextField(
        modifier = modifier
            .background(
                FinanceHelperTheme.colors.defaultButtonColor.copy(0.5f),
                shape = FinanceHelperTheme.shape.shapeRoundMedium
            ),
        value = value,
        textLimitConfig = TransactionNameTextLimit(
            minTextLengthErrorMessage = stringResource(id = R.string.minLengthError, 1),
            maxTextLengthErrorMessage = stringResource(id = R.string.maxLengthError, 100)
        ),
        onValueChange = onValueChange,
        onError = onError,
        maxLines = 1,
        maxLengthPostfixVisibility = false,
        placeHolder = {
            Text(
                text = stringResource(R.string.name),
                style = FinanceHelperTheme.typography.h3,
                color = Color.Black.copy(0.5f)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        textStyle = FinanceHelperTheme.typography.h3
    )
}

@Composable
fun GoodsCountText(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onError: (String?) -> Unit
) {
    val expanded = rememberSaveable() {
        mutableStateOf(false)
    }
    val keyboardState = keyboardAsState()
    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequester by remember { mutableStateOf(FocusRequester()) }

    LaunchedEffect(key1 = keyboardState.value) {
        if (keyboardState.value == KeyboardState.CLOSED) {
            Log.e("QQ", "GoodsCountText:${expanded.value} ")
            expanded.value = false
        }
    }

    CharacterLimitTextField(
        modifier = modifier
            .animateContentSize()
            .width(if (expanded.value) 200.dp else 40.dp)
            .focusRequester(focusRequester)
            .onFocusEvent {
                if(it.isFocused || it.isCaptured) {
                    expanded.value = true
                    // focusRequester.requestFocus()
                    keyboard?.show()
                }
            },
        value = value,
        onValueChange = onValueChange,
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



