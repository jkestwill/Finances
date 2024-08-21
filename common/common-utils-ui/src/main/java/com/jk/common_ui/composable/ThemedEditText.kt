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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.error
import kotlinx.coroutines.delay


@Composable
fun CharacterLimitTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle.Default,
    maxLines: Int = 1,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions=KeyboardActions(),
    textLimitConfig: TextLimitConfig? = null,
    maxLengthPostfixVisibility: Boolean = true,

    onError: (String?) -> Unit,
    prefix: @Composable (() -> Unit)? = null,
    placeHolder: @Composable (() -> Unit)? = null
) {

    val error = remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(error.value) {
        onError(error.value)
        Log.e("CharacterLimitTextField", "error state: ${error}")
        delay(500)
        error.value = null
    }
    LaunchedEffect(key1 = value) {
        val errorMatcher = textLimitConfig?.matchTextLimit(value)
        if (errorMatcher != null) {
            error.value = errorMatcher.first
        }
    }
    ThemedTextField(
        modifier = modifier.error(error.value != null),
        value = value,
        onValueChange = {
            if (textLimitConfig != null) {
                val errorMatcher = textLimitConfig.matchTextLimit(it)
                error.value = errorMatcher?.first
                if (textLimitConfig.isTypingAllowed(it))
                    onValueChange(textLimitConfig.textLimit.onValueChange?.invoke(it) ?: it)
            } else {
                onValueChange(it)
            }
        },
        textStyle = textStyle,
        maxLines = maxLines,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions=keyboardActions,
        textLimitConfig = textLimitConfig,
        maxLengthPostfixVisibility = maxLengthPostfixVisibility,
        prefix = prefix,
        placeHolder = placeHolder
    )
}

@Composable
fun CharacterLimitTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    textStyle: TextStyle = TextStyle.Default,
    maxLines: Int = 1,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textLimitConfig: TextLimitConfig? = null,
    maxLengthPostfixVisibility: Boolean = true,
    onError: (String?) -> Unit,
    prefix: @Composable (() -> Unit)? = null,
    placeHolder: @Composable (() -> Unit)? = null
) {

    val error = remember {
        mutableStateOf<String?>(null)
    }
    LaunchedEffect(error.value) {
        onError(error.value)
        error.value = null
    }

    ThemedEditTextCursorHandle(
        modifier = modifier,
        value = value,
        textStyle = textStyle,
        maxLines = maxLines,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        textLimitConfig = textLimitConfig,
        maxLengthPostfixVisibility = maxLengthPostfixVisibility,
        onValueChange = {
            if (textLimitConfig != null) {
                val errorMatcher = textLimitConfig.matchTextLimit(it.text)
                error.value = errorMatcher?.first
                if (errorMatcher?.second?.isTypingAllowed == true || error.value == null) {
                    val textFieldValue = TextFieldValue(
                        text = textLimitConfig.textLimit.onValueChange?.invoke(it.text) ?: it.text,
                        it.selection
                    )
                    onValueChange(textFieldValue)
                }
            } else {
                onValueChange(it)
            }

        },
        prefix = prefix,
        placeHolder = placeHolder
    )
}

@Composable
fun ThemedEditTextCursorHandle(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    textStyle: TextStyle = TextStyle.Default,
    maxLines: Int = 1,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textLimitConfig: TextLimitConfig? = null,
    maxLengthPostfixVisibility: Boolean = true,
    prefix: @Composable (() -> Unit)? = null,
    placeHolder: @Composable (() -> Unit)? = null
) {
    val postfix: (@Composable () -> Unit)? = if (maxLengthPostfixVisibility) {
        ({
            if (textLimitConfig != null)
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Unspecified),
                    text = "${value.text.length}/${textLimitConfig.textLimit.maxLength.value}",
                    textAlign = TextAlign.End,
                    style = FinanceHelperTheme.typography.h3
                )
        })
    } else null

    ThemedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeHolder = {
            placeHolder?.invoke()
        },
        prefix = prefix,
        postfix = postfix,
        singleLine = singleLine,
        textStyle = textStyle,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
    )
}

@Composable
fun ThemedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle.Default,
    maxLines: Int = 1,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions=KeyboardActions(),
    textLimitConfig: TextLimitConfig? = null,
    maxLengthPostfixVisibility: Boolean = true,
    prefix: @Composable (() -> Unit)? = null,
    placeHolder: @Composable (() -> Unit)? = null
) {

    val post: (@Composable () -> Unit)? = if (maxLengthPostfixVisibility) {
        ({
            if (textLimitConfig != null)
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Unspecified),
                    text = "${value.length}/${textLimitConfig.textLimit.maxLength.value}",
                    textAlign = TextAlign.End,
                    style = FinanceHelperTheme.typography.h3
                )
        })
    } else null
    ThemedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeHolder = {
            placeHolder?.invoke()
        },
        prefix = prefix,
        postfix = post,
        singleLine = singleLine,
        textStyle = textStyle,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,

        )
}


@Composable
fun ThemedTextField(
    modifier: Modifier = Modifier,
    value: String,
    textStyle: TextStyle = TextStyle.Default,
    maxLines: Int = 1,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
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
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        interactionSource = remember { MutableInteractionSource() },
        decorationBox = {
            DecorationBoxTextField(value = value, innerTextField = it, placeHolder, prefix, postfix)
        })
}


@Composable
fun ThemedTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    textStyle: TextStyle = TextStyle.Default,
    maxLines: Int = 1,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions= KeyboardActions(),
    onValueChange: (TextFieldValue) -> Unit,
    placeHolder: (@Composable () -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    postfix: @Composable (() -> Unit)? = null
) {
    BasicTextField(modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        maxLines = maxLines,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = remember { MutableInteractionSource() },
        decorationBox = {
            DecorationBoxTextField(
                value = value.text,
                innerTextField = it,
                placeHolder,
                prefix,
                postfix
            )
        })
}

@Composable
internal fun DecorationBoxTextField(
    value: String,
    innerTextField: @Composable () -> Unit,
    placeHolder: (@Composable () -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    postfix: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(FinanceHelperTheme.shape.headerPadding),
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
            innerTextField()
        }

        if (postfix != null)
            Box(
                modifier = Modifier
                    .weight(1f)

                    .background(Color.Transparent)

            ) {
                postfix()
            }
    }
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