package com.jk.common_data

/**
 * Default exception for app's exceptions.
 * @param scope Scope where exception thrown.
 * */
open class FinanceHelperException(message:String?,val scope:String):Throwable(message) {
    override val message: String?
        get() = "$scope: ${super.message}"
}