package com.jk.financehelper.ui.common


/**
 *@param allowedSpecialCharacters allowedCharacters  - The only specials characters that can be used in text field. If null - all characters allowed.
 *@param requiredCharacters requiredCharacters  - Required specials characters that must be used in text field. If null - all characters allowed;
 * */
sealed class TextLimit(
    val minLength: Int,
    val maxLength: Int,
    val allowedSpecialCharacters: List<Char>? = null,
    val requiredCharacters: List<Char>? = null
) {
    class TextSearchLimit : TextLimit(minLength = 0, maxLength = 32)
    class CategoryNameLimit : TextLimit(minLength = 3, maxLength = 32)

}