package com.jk.transaction

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.input.rememberTextFieldState
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
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

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = onDone),
        textStyle = FinanceHelperTheme.typography.h3
    )
}


@Composable
fun RowScope.ExpandableTextField(block: @Composable RowScope.(Modifier) -> Unit) {
    val expanded = rememberSaveable() {
        mutableStateOf(false)
    }
    val focusRequester by remember { mutableStateOf(FocusRequester()) }

    block(Modifier
        .onFocusEvent {
            if (!it.isFocused) {
                expanded.value = false
            }
        }
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
            .animateContentSize()
            .focusRequester(focusRequester)
            .background(
                FinanceHelperTheme.colors.defaultButtonColor.copy(0.5f),
                shape = FinanceHelperTheme.shape.shapeRoundMedium
            )
           ,
        value = value,
        onClick = {
            expanded.value = true
        },
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
            expanded.value = false
        }
    }
    LaunchedEffect(key1 = expanded.value) {
        if (expanded.value) {
            keyboard?.show()
        } else {
            keyboard?.hide()
        }
    }
    CharacterLimitTextField(
        modifier = modifier
            .animateContentSize()
            .width(if (expanded.value) 150.dp else 40.dp)
            .focusRequester(focusRequester)
            .onFocusEvent {
                if (!it.isFocused) {
                    Log.e("SAS", "GoodsCountText:captured")
                    expanded.value = false
                }
            },
        value = value,
        onValueChange = onValueChange,
        onClick = {
            expanded.value = true
        },
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



