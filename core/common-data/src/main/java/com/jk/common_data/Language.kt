package com.jk.common_data

data class Language(
    val abbreviation:String,
    val regexPattern:String
) {
}

object Languages {
    val ENG = Language("en","a-zA-z")
    val RU = Language("ru","а-яА-я")
}