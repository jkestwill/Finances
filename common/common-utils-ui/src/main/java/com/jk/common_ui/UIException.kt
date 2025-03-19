package com.jk.common_ui

import com.jk.common_data.FinanceHelperException

/**
 * Should be thrown then user typing wrong values in text fields or makes logical mistakes
 *
 * * */
open class UIException(message:String?):FinanceHelperException(message=message,scope="ui_scope")