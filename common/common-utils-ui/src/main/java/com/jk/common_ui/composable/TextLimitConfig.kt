package com.jk.common_ui.composable


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
    val regexPattern: Limit<String>? = null,
    val filter: ((String) -> Boolean)? = null,
    val onValueChange: ((String) -> String)? = null
)

open class TextLimitConfig(
    val textLimit: TextLimit,
    val error: TextError
) {
    private fun checkText(
        text: String,
    ): Pair<String, Limit<out Any>?> {
        val regex: Regex? =
            if (textLimit.regexPattern != null) Regex(textLimit.regexPattern.value) else null
        return when {
            text.length > textLimit.maxLength.value -> {
                Pair(error.maxTextLengthError, textLimit.maxLength)
            }

            text.length < textLimit.minLength.value -> {
                Pair(error.minTextLengthError, textLimit.minLength)
            }

            !text.all {
                it.isLetterOrDigit() || textLimit.allowedSpecialCharacters?.value?.contains(
                    it
                ) ?: true
            } -> {
                Pair(
                    error.allowedCharactersError,
                    textLimit.allowedSpecialCharacters
                )
            }

            regex != null && !regex.matches(text) -> {
                Pair("Wrong pattern", textLimit.regexPattern)

            }

            else -> {
                throw IllegalStateException("Wrong textLimit state $this")
            }
        }
    }

    fun matchTextLimit(
        text: String
    ): Pair<String, Limit<out Any>?>? {
        return try {
            checkText(text)
        } catch (e: Throwable) {
            null
        }
    }

    fun isTypingAllowed(text: String): Boolean {
        val matcher = matchTextLimit(text)
        return matcher == null || matcher.second?.isTypingAllowed == true
    }
}



