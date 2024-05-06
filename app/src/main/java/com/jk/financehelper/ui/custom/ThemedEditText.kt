package com.jk.financehelper.ui.custom

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jk.common_data.Languages
import com.jk.financehelper.ui.common.Limit
import com.jk.financehelper.ui.common.TextError
import com.jk.financehelper.ui.common.TextLimit
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
    onError: (String?) -> Unit
) {
    val textError = rememberTextError(textLimit = textLimit)
    val error = remember {
        mutableStateOf<String?>(null)
    }
    LaunchedEffect(error.value) {
        onError(error.value)
        Log.e("ERROR", "CharacterLimitTextField:${error.value} ")
    }
    ThemedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            val errorMatcher = matchTextLimit(it, textLimit = textLimit, textError)
            error.value = errorMatcher?.first
            if (errorMatcher?.second?.isTypingAllowed == true || error.value == null)
                onValueChange(it)
        },
        placeHolder = {
            Text("Search")
        },
        postfix = {
                  Text(text = "${value.length}/${textLimit.maxLength.value}")
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
): Pair<String, Limit<out Any>?> {

    return when {
        text.length > textLimit.maxLength.value -> {
            Pair(textError.maxTextLengthError, textLimit.maxLength)
        }

        text.length <= textLimit.minLength.value -> {
            Pair(textError.minTextLengthError, textLimit.minLength)
        }

        !text.all {
            it == '\u0000' || it.isLetterOrDigit() || textLimit.allowedSpecialCharacters?.value?.contains(
                it
            ) ?: true
        } -> {
            Pair(textError.allowedCharactersError, textLimit.allowedSpecialCharacters)
        }

        else -> {
            throw IllegalStateException("Wrong textLimit state")
        }
    }
}

fun matchTextLimit(
    text: String,
    textLimit: TextLimit,
    textError: TextError
): Pair<String, Limit<out Any>?>? {
    val pattern = "[${Languages.ENG}${Languages.RU}]"
    return try {
        throwExceptionIfNotMatch(text, textLimit, textError, pattern)
    } catch (e: IllegalStateException) {
        e.printStackTrace()
        null
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