package com.jk.common_ui


/**
*Interface for handling error messages from resource depends on exception
* */
interface StringResourceExceptionHandler<in T:Throwable> {

    fun handle(exception:T):String
}

