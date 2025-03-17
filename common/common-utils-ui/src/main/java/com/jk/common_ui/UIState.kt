package com.jk.common_ui

import com.jk.common_data.ApiRequest


fun <T : Any> ApiRequest<T>.toState(): UIState<T> {
    return when (this) {
        is ApiRequest.Success -> {
            UIState.Success(data)
        }

        is ApiRequest.Error -> {
           UIState.Error(data = data, message = error?.message ?: "")
        }

        is ApiRequest.Loading -> {
            UIState.Loading(data)
        }
    }
}

sealed class UIState<out T : Any> {
    data object None : UIState<Nothing>()
    class Success<T : Any>(val data: T) : UIState<T>()
    class Loading<T : Any>(val data: T?) : UIState<T>()
    class Error<T : Any>(val data: T?, val message: String) : UIState<T>()

}

fun <I : Any, O : Any> UIState<I>.map(mapper: (I) -> O): UIState<O> {
    return when (this) {
        is UIState.None -> {
            UIState.None
        }

        is UIState.Success -> {
            UIState.Success(mapper(data))
        }

        is UIState.Loading -> {
            UIState.Loading(data?.let { mapper(it) })
        }

        is UIState.Error -> {
            UIState.Error(data = data?.let { mapper(it) }, message = message)
        }
    }
}