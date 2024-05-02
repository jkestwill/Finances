package com.jk.financehelper.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import com.jk.financehelper.R


/**
 *@param allowedSpecialCharacters allowedCharacters  - The only specials characters that can be used in text field. If null - all characters allowed.
 *@param requiredCharacters requiredCharacters  - Required specials characters that must be used in text field. If null - all characters allowed;
 * */
class TextLimit(
    val minLength: Int,
    val maxLength: Int,
    val allowedSpecialCharacters: List<Char>? = null,
    val requiredCharacters: List<Char>? = null,
) {


}

sealed class TextConfig(
    val textLimit: TextLimit,
    val textError: TextError
) {
    class TextSearchConfig() : TextConfig(
        textLimit = TextLimit(minLength = 0, maxLength = 32),
        textError = TextError(
            minTextLengthError = StringResource(R.string.minLengthError, arrayOf(0)),
            maxTextLengthError = StringResource(
                R.string.maxLengthError,
                arrayOf(32)
            )
        )
    ) {

    }
}

class TextError(
    val minTextLengthError: ComposeString = StringResource(R.string.error),
    val maxTextLengthError: ComposeString = StringResource(R.string.error),
    val allowedCharactersError: ComposeString = StringResource(R.string.error),
    val wrongNameError: ComposeString = StringResource(R.string.error)
) {

}

interface ComposeString {
    val value: String
        @Composable
        @ReadOnlyComposable
        get
}

class StringResource(val resId: Int, val args: Array<Any> = arrayOf()) : ComposeString {
    override val value: String
        @Composable
        @ReadOnlyComposable
        get() = stringResource(id = resId, args)
}
