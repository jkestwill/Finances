package com.jk.common_data


sealed class ApiRequest<out T : Any>(open val data: T? = null) {

    class Loading<T : Any>(data: T? = null) : ApiRequest<T>(data)
    class Success<T : Any>(override val data: T) : ApiRequest<T>(data)
    class Error<T : Any>(data: T? = null, val error: Throwable? = null) : ApiRequest<T>(data)
}


inline fun <I : Any, O : Any> ApiRequest<I>.map(mapper: (I) -> O): ApiRequest<O> {
   return when (this) {
        is ApiRequest.Loading -> ApiRequest.Loading(data?.let { mapper(it) })
        is ApiRequest.Success -> ApiRequest.Success(mapper(data))
        is ApiRequest.Error -> ApiRequest.Error(data?.let { mapper(it) })
    }
}