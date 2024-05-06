package com.jk.financehelper.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import com.jk.financehelper.R

@Composable
fun rememberTextError(textLimit: TextLimit): TextError {
    val textErrorFromResource = TextErrorFromResource.create(textLimit)

    return rememberTextError(textErrorFromResource)

}

@Composable
private fun rememberTextError(
    textComposableError: TextErrorFromResource
): TextError {
    val plainMinTextLengthError = textComposableError.minTextLengthError.value
    val plainMaxTextLengthError = textComposableError.maxTextLengthError.value
    val plainAllowedCharactersError = textComposableError.allowedCharactersError.value

    return TextError(plainMinTextLengthError, plainMaxTextLengthError, plainAllowedCharactersError)
}

data class TextError internal constructor(
    val minTextLengthError: String = "",
    val maxTextLengthError: String = "",
    val allowedCharactersError: String = "",
    val wrongNameError: String = ""
)

enum class FieldType {
    DEFAULT, SEARCH
}


/**
 *@param allowedSpecialCharacters allowedCharacters  - The only specials characters that can be used in text field. If null - all characters allowed.
 *@param requiredCharacters requiredCharacters  - Required specials characters that must be used in text field. If null - all characters allowed;
 * */
sealed class TextLimit(
    val minLength: Int,
    val maxLength: Int,
    val allowedSpecialCharacters: List<Char>? = null,
    val requiredCharacters: List<Char>? = null,
) {
    class SearchTextLimit() : TextLimit(0, 32)

}

class TextErrorFromResource private constructor(
    val minTextLengthError: ComposeString = ComposeString.StringResource(R.string.error),
    val maxTextLengthError: ComposeString = ComposeString.StringResource(R.string.error),
    val allowedCharactersError: ComposeString = ComposeString.StringResource(R.string.error),
    val wrongNameError: ComposeString = ComposeString.StringResource(R.string.error)
) {
    companion object {
        fun create(textLimit: TextLimit): TextErrorFromResource {
            return TextErrorFromResource(
                minTextLengthError = ComposeString.StringResource(
                    R.string.minLengthError,
                    listOf(textLimit.minLength)
                ),
                maxTextLengthError = ComposeString.StringResource(
                    R.string.maxLengthError,
                    listOf(textLimit.maxLength)
                ),
                allowedCharactersError = ComposeString.StringResource(
                    R.string.allowedCharacters,
                    textLimit.allowedSpecialCharacters?.let { listOf(it.toString()) } ?: listOf("")
                ),
            )
        }
    }
}


sealed interface ComposeString {
    val value: String
        @Composable
        @ReadOnlyComposable
        get

    class StringResource(val resId: Int, val args: List<Any> = listOf()) : ComposeString {

        override val value: String
            @Composable
            @ReadOnlyComposable
            get() = stringResource(id = resId, *args.toTypedArray())


    }

    class Plain(override val value: String) : ComposeString
}



