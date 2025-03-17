package com.jk.common_data

/**
 * Default exception for app's exceptions.
 * @param scope Scope where exception thrown.
 * */
open class FinanceHelperException(message:String?,val scope:String="none"):Throwable(message) {
    override val message: String?
        get() = "$scope: ${super.message}"
}

sealed class AppErrors(message: String?):FinanceHelperException(message){

    data object NotEnoughSpace:AppErrors("not enough disk space") {
        private fun readResolve(): Any = NotEnoughSpace
    }

    data object UnknowonError:AppErrors("unknown error") {
        private fun readResolve(): Any = UnknowonError
    }

    data object ListIsEmpty:AppErrors("List is empty") {
        private fun readResolve(): Any = ListIsEmpty
    }

    data object  NoInternetConnection:AppErrors("No internet connection") {
        private fun readResolve(): Any = NoInternetConnection
    }

    data object TimeOutConnectionException:AppErrors("Connection times out") {
        private fun readResolve(): Any = TimeOutConnectionException
    }


}

