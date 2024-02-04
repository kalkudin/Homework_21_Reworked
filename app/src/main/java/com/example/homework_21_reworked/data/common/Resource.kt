package com.example.homework_21_reworked.data.common

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val statusCode: Int, val errorType: ErrorType) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}

sealed class ErrorType {
    data object NetworkError : ErrorType()
    data object AuthenticationError : ErrorType()
    data object NotFound : ErrorType()
    data object AccessDenied : ErrorType()
    data object ServiceUnavailable : ErrorType()
    data object EmptyDatabase : ErrorType()
    data class UnknownError(val errorMessage: String) : ErrorType()
}