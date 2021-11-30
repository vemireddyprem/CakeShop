package com.prem.cakeshop.common

sealed class DataRetrievalStatus<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>() : DataRetrievalStatus<T>()
    class Success<T>(data: T? = null) : DataRetrievalStatus<T>(data)
    class Error<T>(message: String? = null) : DataRetrievalStatus<T>(null, message)
}