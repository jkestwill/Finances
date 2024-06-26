package com.jk.common_data

sealed class State<T : Any> {
    class None<T : Any> : State<T>()
    class Success<T : Any>(val data: T) : State<T>()
    class Loading<T : Any>(val data: T?) : State<T>()
    class Error<T : Any>(val data: T?, val message: String) : State<T>()

}

fun <T : Any> ApiRequest<T>.toState(): State<T> {
   return when (this) {
        is ApiRequest.Success -> State.Success(data)
        is ApiRequest.Error -> State.Error(data = data, message = error?.message ?: "")
        is ApiRequest.Loading -> State.Loading(data = data)
    }
}