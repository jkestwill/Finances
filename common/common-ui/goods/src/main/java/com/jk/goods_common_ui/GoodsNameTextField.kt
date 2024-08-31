package com.jk.goods_common_ui

import androidx.compose.foundation.background
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
fun GoodsNameText(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onExpand: (() -> Unit)? = null,
    onError: (String?) -> Unit
) {
    CharacterLimitTextField(
        modifier = modifier
            .background(
                FinanceHelperTheme.colors.defaultButtonColor.copy(0.5f),
                shape = FinanceHelperTheme.shape.shapeRoundMedium
            ),
        value = value,
        onClick = onExpand,
        textLimitConfig = GoodsNameTextLimit(
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