package com.finals.pokdex.utils

sealed class Resources<T>(
    val data: T? = null,
    val error: String? = null
) {
    class Loading<T>() : Resources<T>()
    class Success<T>(data: T) : Resources<T>(data)
    class Error<T>(message: String) : Resources<T>(error = message)
}
