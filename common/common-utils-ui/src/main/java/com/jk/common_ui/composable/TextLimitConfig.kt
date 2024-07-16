package com.jk.common_ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource


data class TextError(
    val minTextLengthError: String = "",
    val maxTextLengthError: String = "",
    val allowedCharactersError: String = "",
    val wrongNameError: String = ""
)


/**
 *@param allowedSpecialCharacters allowedCharacters  - The only specials characters that can be used in text field. If null - all characters allowed.
 *@param requiredCharacters requiredCharacters  - Required specials characters that must be used in text field. If null - all characters allowed;
 * */


data class Limit<T>(
    val value: T,
    val isTypingAllowed: Boolean
)

data class TextLimit(
    val minLength: Limit<Int>,
    val maxLength: Limit<Int>,
    val allowedSpecialCharacters: Limit<List<Char>>? = null,
    val requiredCharacters: Limit<List<Char>>? = null,
)

open class TextLimitConfig(
    val textLimit: TextLimit,
    val error: TextError
)
class TextErrorFromResource(
    val minTextLengthError: ComposeString,
    val maxTextLengthError: ComposeString,
    val allowedCharactersError: ComposeString,
    val wrongNameError: ComposeString
) {
    companion object {
//        fun create(textLimit: TextLimit): TextErrorFromResource {
//            return TextErrorFromResource(
//                minTextLengthError = ComposeString.StringResource(
//                    R.string.minLengthError,
//                    listOf(textLimit.minLength.value)
//                ),
//                maxTextLengthError = ComposeString.StringResource(
//                    R.string.maxLengthError,
//                    listOf(textLimit.maxLength.value)
//                ),
//                allowedCharactersError = ComposeString.StringResource(
//                    R.string.allowedCharacters,
//                    textLimit.allowedSpecialCharacters?.let {limit->
//                        listOf(limit.value)
//                    }
//                        ?: listOf("")
//                ),
//            )
//        }
    }
}


sealed interface ComposeString {
    val value: String
        @Composable
        @ReadOnlyComposable
        get

    data class StringResource(val resId: Int, val args: List<Any> = listOf()) : ComposeString {

        override val value: String
            @Composable
            @ReadOnlyComposable
            get() = stringResource(id = resId, *args.toTypedArray())


    }

    data class Plain(override val value: String) : ComposeString
}



