package com.jk.common_data




sealed class ApiRequest<T>(open val data: T? = null) {

    class Loading<T>(data: T? = null) : ApiRequest<T>(data)
    class Success<T>(override val data: T) : ApiRequest<T>(data)
    class Error<T>(data: T? = null, val error: Throwable? = null) : ApiRequest<T>(data)
}