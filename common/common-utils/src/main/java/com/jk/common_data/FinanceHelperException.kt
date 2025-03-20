package com.jk.common_data

/**
 * Default exception for app's exceptions.
 * @param scope Scope where exception thrown.
 * */
abstract class FinanceHelperException(message: String?, val scope: String = "none") : Throwable(message) {
    override val message: String?
        get() = "$scope: ${super.message}"

}


