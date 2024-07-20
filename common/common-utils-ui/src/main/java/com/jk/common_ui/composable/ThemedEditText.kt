package com.jk.common_ui.composable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.shake


@Composable
fun CharacterLimitTextField(
    modifier: Modifier = Modifier,
    value: String,
    textStyle: TextStyle = TextStyle.Default,
    maxLines: Int = 1,
    textLimitConfig: TextLimitConfig? = null,
    onValueChange: (String) -> Unit,
    onError: (String?) -> Unit,
    prefix: @Composable (() -> Unit)? = null,
    placeHolder: @Composable (() -> Unit)? = null
) {
    val error = remember {
        mutableStateOf<String?>(null)
    }
    LaunchedEffect(error.value) {
        onError(error.value)
        Log.e("ERROR", "CharacterLimitTextField:${error.value} ")
    }
    ThemedTextField(
        modifier = modifier
            .shake(error.value != null)
           ,
        value = value,
        onValueChange = {
            if (textLimitConfig != null) {
                val errorMatcher = matchTextLimit(it, textLimit = textLimitConfig)
                error.value = errorMatcher?.first
                if (errorMatcher?.second?.isTypingAllowed == true || error.value == null)
                    onValueChange(it)
            } else {
                onValueChange(it)
            }
        },
        placeHolder = {
            placeHolder?.invoke()
        },
        prefix = prefix,
        postfix = {
            if (textLimitConfig != null)
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Unspecified),
                    text = "${value.length}/${textLimitConfig.textLimit.maxLength.value}",
                    textAlign = TextAlign.End,
                    style = FinanceHelperTheme.typography.h3,

                    )
        },
        textStyle = textStyle,
        maxLines = maxLines,
    )
}


private fun checkText(
    text: String,
    textLimitConfig: TextLimitConfig,
): Pair<String, Limit<out Any>?> {

    return when {
        text.length > textLimitConfig.textLimit.maxLength.value -> {
            Pair(textLimitConfig.error.maxTextLengthError, textLimitConfig.textLimit.maxLength)
        }

        text.length < textLimitConfig.textLimit.minLength.value -> {
            Pair(textLimitConfig.error.minTextLengthError, textLimitConfig.textLimit.minLength)
        }

        !text.all {
            it == '\u0000' || it.isLetterOrDigit() || textLimitConfig.textLimit.allowedSpecialCharacters?.value?.contains(
                it
            ) ?: true
        } -> {
            Pair(
                textLimitConfig.error.allowedCharactersError,
                textLimitConfig.textLimit.allowedSpecialCharacters
            )
        }

        else -> {
            throw IllegalStateException("Wrong textLimit state ${textLimitConfig}")
        }
    }
}

private fun matchTextLimit(
    text: String,
    textLimit: TextLimitConfig,
): Pair<String, Limit<out Any>?>? {
    return try {
        checkText(text, textLimit)
    } catch (e: Throwable) {
       // e.printStackTrace()
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
        interactionSource = remember { MutableInteractionSource() },
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
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Transparent)
                    ) {
                        prefix()
                    }

                Box(Modifier.weight(2f)) {
                    if (value.isEmpty()) {
                        placeHolder?.invoke()
                    }
                    it()
                }

                if (postfix != null)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp)
                            .background(Color.Transparent)

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