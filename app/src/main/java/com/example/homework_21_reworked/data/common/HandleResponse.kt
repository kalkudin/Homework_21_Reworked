package com.example.homework_21_reworked.data.common

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class HandleResponse @Inject constructor() {
    fun <T : Any> handleApiCall(apiCall: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        try {
            Log.d("HandleResponse", "Starting API call")
            emit(Resource.Loading)
            val response = apiCall()
            if (response.isSuccessful) {
                Log.d("HandleResponse", "API call successful")
                emit(Resource.Success(response.body() ?: throw NullPointerException("Response body is null")))
            } else {
                Log.d("HandleResponse", "API call failed: Error Code: ${response.code()}")
                val errorType = when (response.code()) {
                    401 -> ErrorType.AuthenticationError
                    403 -> ErrorType.AccessDenied
                    404 -> ErrorType.NotFound
                    503 -> ErrorType.ServiceUnavailable
                    else -> ErrorType.UnknownError("Unknown Error Code: ${response.code()}")
                }
                emit(Resource.Error(response.code(), errorType))
            }
        } catch (e: IOException) {
            Log.e("HandleResponse", "Network error: ${e.localizedMessage}", e)
            emit(Resource.Error(0, ErrorType.NetworkError))
        } catch (e: Exception) {
            Log.e("HandleResponse", "Unknown error: ${e.localizedMessage}", e)
            e.localizedMessage?.let { ErrorType.UnknownError(it) }
                ?.let { Resource.Error(0, it) }?.let { emit(it) }
        }
    }
}