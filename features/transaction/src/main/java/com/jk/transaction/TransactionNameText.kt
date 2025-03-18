package com.jk.transaction

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
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
                color = FinanceHelperTheme.colors.defaultButtonColor.copy(0.5f),
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


/**
 * TextField with expandable behavior in [RowScope]
 * @param defaultWeight - applies this RowScope weight whe collapsed
 * @param expandedWidth - apply this width when expanded
 * */
@Composable
fun RowScope.ExpandableTextField(
    defaultWeight: Float,
    expandedWidth: Int,
    block: @Composable RowScope.(Modifier, () -> Unit) -> Unit
) {
    val keyboardState = keyboardAsState()

    val expanded = rememberSaveable() {
        mutableStateOf(false)
    }
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val size = Modifier.width(expandedWidth.dp)
    val weight = Modifier.weight(defaultWeight)

    LaunchedEffect(key1 = keyboardState.value) {
        if (keyboardState.value == KeyboardState.CLOSED) {
            expanded.value = false
        }
    }
    block(
        Modifier
            .onFocusEvent {
                if (!it.isFocused) {
                    expanded.value = false
                }
            }
            .onFocusEvent {
                if (!it.isFocused) {
                    Log.e("SAS", "GoodsCountText:captured")
                    expanded.value = false
                }
            }
            .focusRequester(focusRequester)
            .animateContentSize()
            .then(if (expanded.value) size else weight)

    ) {
        expanded.value = true
    }
}








