package com.jk.exchange_rate_data

import com.jk.common_data.ApiRequest

interface MergeStrategy<T> {

    fun merge(right: T, left: T): T
}

// ДОРАБОТАТЬ ВСЕ СЛУЧАИ
internal class ApiRequestMergeStrategy<T:Any> : MergeStrategy<ApiRequest<T>> {

    override fun merge(right: ApiRequest<T>, left: ApiRequest<T>): ApiRequest<T> {
        return when {
            right is ApiRequest.Loading && left is ApiRequest.Loading -> merge(right, left)
            right is ApiRequest.Success<T> && left is ApiRequest.Success<T> -> merge(right, left)
            right is ApiRequest.Error && left is ApiRequest.Error -> merge(right, left)
            right is ApiRequest.Success && left is ApiRequest.Loading -> merge(right, left)
            right is ApiRequest.Success && left is ApiRequest.Error -> merge(right, left)
            right is ApiRequest.Error && left is ApiRequest.Success -> merge(right, left)
            right is ApiRequest.Error<T> && left is ApiRequest.Loading<T> -> merge(right, left)
            right is ApiRequest.Loading && left is ApiRequest.Error -> merge(right, left)
            right is ApiRequest.Loading && left is ApiRequest.Success<T> -> merge(right, left)
            else -> error("Unknown states ${right} ${left}")
        }
    }

    private fun merge(right: ApiRequest.Loading<T>, left: ApiRequest.Loading<T>): ApiRequest<T> {
        return if (left.data != null) return ApiRequest.Loading(left.data)
        else ApiRequest.Loading(right.data)
    }

    private fun merge(right: ApiRequest.Success<T>, left: ApiRequest.Loading<T>): ApiRequest<T> {
        return ApiRequest.Loading(right.data)
    }

    private fun merge(right: ApiRequest.Success<T>, left: ApiRequest.Error<T>): ApiRequest<T> {
        return ApiRequest.Error(right.data, left.error)
    }

    private fun merge(right: ApiRequest.Error<T>, left: ApiRequest.Error<T>): ApiRequest<T> {
        return ApiRequest.Error(right.data, right.error)
    }

    private fun merge(right: ApiRequest.Error<T>, left: ApiRequest.Success<T>): ApiRequest<T> {
        return ApiRequest.Error(left.data, right.error)
    }

    private fun merge(right: ApiRequest.Loading<T>, left: ApiRequest.Error<T>): ApiRequest<T> {
        return ApiRequest.Error(right.data, left.error)
    }

    private fun merge(right: ApiRequest.Error<T>, left: ApiRequest.Loading<T>): ApiRequest<T> {
        return ApiRequest.Error(left.data, right.error)
    }

    private fun merge(right: ApiRequest.Success<T>, left: ApiRequest.Success<T>): ApiRequest<T> {
        return ApiRequest.Success(right.data ?: left.data)
    }
    private fun merge(right: ApiRequest.Loading<T>, left: ApiRequest.Success<T>): ApiRequest<T> {
        return ApiRequest.Loading(left.data)
    }
}
