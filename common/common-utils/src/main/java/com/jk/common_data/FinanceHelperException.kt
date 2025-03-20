package com.jk.common_data

/**
 * Default exception for app's exceptions.
 * @param scope Scope where exception thrown.
 * */
sealed class FinanceHelperException(message: String?, val scope: String = "none") :
    Throwable(message) {
    override val message: String?
        get() = "$scope: ${super.message}"
    // ошибки соединения
    open class NetworkException() : FinanceHelperException("network exception")

    // ошибки зависящие от пользователя
    open class BusinessLogicException() : FinanceHelperException("business exception")
    // ошибки по типу нехватки памяти в системе
    open class SystemException() : FinanceHelperException("storage exception")


}

fun catchAppException(){

}