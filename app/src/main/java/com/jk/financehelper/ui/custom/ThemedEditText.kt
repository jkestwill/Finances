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
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jk.common_data.Languages
import com.jk.financehelper.R
import com.jk.financehelper.ui.common.TextLimit
import com.jk.financehelper.ui.common.TextError
import com.jk.financehelper.ui.common.rememberTextError
import com.jk.financehelper.ui.theme.FinanceHelperTheme

@Composable
fun CharacterLimitTextField(
    modifier: Modifier = Modifier,
    value: String,
    textStyle: TextStyle = TextStyle.Default,
    maxLines: Int = 1,
    textLimit: TextLimit,
    onValueChange: (String) -> Unit,
    onError:(String?)->Unit
) {
    val textError = rememberTextError(textLimit = textLimit)
    val error = remember {
        mutableStateOf<String?>(null)
    }
    LaunchedEffect(error.value) {
        onError(error.value)
    }
    ThemedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            println(it.length)
            println(value.length)
            if (value.isEmpty() || it.length>value.length)
                onValueChange(it)
            error.value = matchTextLimit(it, textLimit = textLimit, textError)
        },
        placeHolder = {
            Text("Search")
        },
        textStyle = textStyle,
        maxLines = maxLines,
    )
}


// разделить на несколько регексов и матчит все вместе для локализации ошибок например длинна больше допустимой и лишний симов то выдаст сначала длинна кароч ты вкурил друк
fun throwExceptionIfNotMatch(
    text: String,
    textLimit: TextLimit,
    textError: TextError,
    pattern: String,
) {
    val regex = Regex(pattern)
    when {
        text.length > textLimit.maxLength -> {
            throw IllegalStateException(textError.maxTextLengthError)
        }

        text.length < textLimit.minLength -> {
            throw IllegalStateException(textError.minTextLengthError)
        }

        text.any {
            !it.isLetter() && textLimit.allowedSpecialCharacters?.contains(it)?.not() ?: true
        } -> {
            throw IllegalStateException(textError.allowedCharactersError)
        }

        regex.matches(text) -> {
            throw IllegalStateException(textError.allowedCharactersError)
        }
    }
}

fun matchTextLimit(text: String, textLimit: TextLimit, textError: TextError): String? {
    val pattern = "[${Languages.ENG}${Languages.RU}]"
    return try {
        throwExceptionIfNotMatch(text, textLimit, textError, pattern)
        null
    } catch (e: IllegalStateException) {
        e.message
    }
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
                        color = FinanceHelperTheme.colors.primaryBackground.copy(alpha = 0.5f),
                        shape = FinanceHelperTheme.shape.shape10
                    )
                    .padding(FinanceHelperTheme.shape.padding),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (prefix != null)
                    Box(modifier = Modifier.weight(1f)) {
                        prefix()
                    }

                Box(Modifier.weight(2f)) {
                    if (value.isEmpty()) {
                        placeHolder?.invoke()
                    } else {
                        it()
                    }
                }

                if (postfix != null)
                    Box(
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