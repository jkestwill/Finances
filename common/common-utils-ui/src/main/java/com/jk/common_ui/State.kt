package com.jk.common_ui

import com.jk.common_data.ApiRequest


fun <T : Any> ApiRequest<T>.toState(): State<T> {
    return when (this) {
        is ApiRequest.Success -> {
            State.Success(data)
        }

        is ApiRequest.Error -> {
           State.Error(data = data, message = error?.message ?: "")
        }

        is ApiRequest.Loading -> {
            State.Loading(data)
        }
    }
}

sealed class State<out T : Any> {
    data object None : State<Nothing>()
    class Success<T : Any>(val data: T) : State<T>()
    class Loading<T : Any>(val data: T?) : State<T>()
    class Error<T : Any>(val data: T?, val message: String) : State<T>()

}

fun <I : Any, O : Any> State<I>.map(mapper: (I) -> O): State<O> {
    return when (this) {
        is State.None -> {
            State.None
        }

        is State.Success -> {
            State.Success(mapper(data))
        }

        is State.Loading -> {
            State.Loading(data?.let { mapper(it) })
        }

        is State.Error -> {
            State.Error(data = data?.let { mapper(it) }, message = message)
        }
    }
}