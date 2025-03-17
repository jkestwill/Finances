package com.jk.common_data

/**
 * Default exception for app's exceptions.
 * @param scope Scope where exception thrown.
 * */
sealed class FinanceHelperException(message: String?, val scope: String = "none") :
    Throwable(message) {
    override val message: String?
        get() = "$scope: ${super.message}"

    open class NetworkException() : FinanceHelperException("network exception")

    open class BusinessLogicException() : FinanceHelperException("business exception")

    open class SystemException() : FinanceHelperException("storage exception")


}

