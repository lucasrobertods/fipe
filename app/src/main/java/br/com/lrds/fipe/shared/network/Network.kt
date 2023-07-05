package br.com.lrds.fipe.shared.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception
import java.net.HttpURLConnection

sealed class NetworkResult<out T> {
    data class Success<T> (val data : T): NetworkResult<T>()
    data class Error(val statusCode: Int?, val message: String?): NetworkResult<Nothing>()
}

private fun <T> Response<T>.parseResponse(): NetworkResult<T> {
    try {
        if (isSuccessful) {
            val body = body()

            if (body != null) {
                return NetworkResult.Success(body)
            }
        } else {
            return NetworkResult.Error(code(), message())
        }
        return NetworkResult.Error(HttpURLConnection.HTTP_INTERNAL_ERROR, message())
    } catch (e : Exception) {
        Log.e("NetworkResultError", e.message, e)
        return NetworkResult.Error(HttpURLConnection.HTTP_INTERNAL_ERROR, e.message)
    }
}

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>) =
    withContext(Dispatchers.IO) {
        try {
            apiCall.invoke().parseResponse()
        } catch (e: Exception) {
            NetworkResult.Error(HttpURLConnection.HTTP_INTERNAL_ERROR, e.message)
        }
    }