package com.jk.financehelper.ui.common

sealed class TextError(
    val minTextLengthError:String="",
    val maxTextLengthError:String="",
    val allowedCharactersError:String="",
    val wrongNameError:String=""
) {

    class SearchTextError():TextError()
}

