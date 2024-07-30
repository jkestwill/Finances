package com.jk.common_data

import java.util.logging.Level
import java.util.logging.Logger

fun logger(tag: String,isDebug:Boolean): Logger? {
    return if(isDebug) Logger.getLogger(tag) else null
}
object LoggerTags{
    const val SELECT_CATEGORY="SelectCategory"
    const val ADD_NEW_TRANSACTION="AddNewTransaction"
}