package com.jk.financehelper.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jk.financehelper.ui.common.TextLimit
import com.jk.financehelper.ui.theme.FinanceHelperTheme

@Composable
fun CharacterLimitTextField(
    modifier: Modifier = Modifier,
    value: String,
    textStyle: TextStyle = TextStyle.Default,
    maxLines: Int = 1,
    textLimit: TextLimit,
    onValueChange: (String) -> Unit,
) {

    ThemedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {

            if (matchTextLimit(value, textLimit = textLimit)) {
                onValueChange(value)
            }

        },
        textStyle = textStyle,
        maxLines = maxLines,
    )
}

// разделить на несколько регексов и матчит все вместе для локализации ошибок например длинна больше допустимой и лишний симов то выдаст сначала длинна кароч ты вкурил друк
fun errorMessage(textLimit: TextLimit) {

}

fun matchTextLimit(text: String, textLimit: TextLimit): Boolean {
    val allowedCharacters = if (textLimit.allowedSpecialCharacters != null) {
        textLimit.allowedSpecialCharacters.joinToString { "" }
    } else ""
    val pattern = "[a-zA-z$allowedCharacters]"

    return isMatch(text, pattern)
}

fun isMatch(string: String, pattern: String): Boolean {
    val regex = Regex.fromLiteral(pattern)

    return regex.matches(string)
}

@Composable
fun ThemedTextField(
    modifier: Modifier = Modifier,
    value: String,
    textStyle: TextStyle = TextStyle.Default,
    maxLines: Int = 1,
    onValueChange: (String) -> Unit,
    placeHolder: (@Composable () -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    postfix: @Composable (() -> Unit)? = null
) {
    BasicTextField(modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        maxLines = maxLines,
        decorationBox = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = FinanceHelperTheme.colors.primaryBackground,
                        shape = FinanceHelperTheme.shape.shape10
                    )
                    .padding(FinanceHelperTheme.shape.padding),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (prefix != null) Box(modifier = Modifier.weight(1f)) {
                    prefix()
                }

                Box(Modifier.weight(2f)) {
                    if (value.isEmpty()) {
                        placeHolder?.invoke()
                    } else {
                        it()
                    }
                }

                if (postfix != null) Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(2.dp)
                ) {
                    postfix()
                }
            }

        })
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun TextFieldPreview() {
    FinanceHelperTheme {
        ThemedTextField(modifier = Modifier
            .width(100.dp)
            .height(40.dp),
            value = "Huizxcwfwkefsda",
            textStyle = FinanceHelperTheme.typography.body,
            onValueChange = {},
            postfix = {

                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Filled.Search,
                    contentDescription = "sas"
                )

            })
    }

}