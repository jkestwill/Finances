package com.jk.common_data

inline fun <reified T> apiRequest(block: () -> T): Response<T> = try {
    Response.success(block())
} catch (e: Throwable) {
    Response.failure(e)
}

sealed class Response<out T> {
    open fun getSuccess(): T =
        throw IllegalStateException("getSuccess() can be called only on successful response")

    open fun getFailureCause(): Throwable =
        throw IllegalStateException("getFailureCause() can be called only on failed response")

    open suspend fun onSuccess(block: suspend (value: T) -> Unit): Response<T> = this

    open suspend fun <S> map(block: suspend (value: T) -> S): Response<S> =
        if (isSuccess())
            success(block(getSuccess()))
        else
            failure(getFailureCause())

    open suspend fun onFailure(block: suspend (cause: Throwable) -> Unit): Response<T> = this

    open fun isSuccess(): Boolean = false

    open fun isFailure(): Boolean = false

    private class Success<out T>(val value: T) : Response<T>() {
        override fun getSuccess(): T = value
        override fun isSuccess() = true
        override suspend fun onSuccess(block: suspend (value: T) -> Unit): Response<T> {
            block(value)
            return this
        }

        override suspend fun <S> map(block: suspend (value: T) -> S): Response<S> {
            return Success(block(getSuccess()))
        }
    }

    private class Failure<out T>(val cause: Throwable) : Response<T>() {
        override fun getFailureCause(): Throwable = cause
        override fun isFailure(): Boolean = true
        override suspend fun onFailure(block: suspend (cause: Throwable) -> Unit): Response<T> {
            block(cause)
            return this
        }
    }

    companion object {

        @JvmStatic
        fun <T> success(value: T): Response<T> = Success(value)

        @JvmStatic
        fun <T> failure(cause: Throwable): Response<T> = Failure(cause)
    }
}