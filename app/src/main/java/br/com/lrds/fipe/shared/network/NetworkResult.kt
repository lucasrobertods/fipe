package br.com.lrds.fipe.shared.network

import android.util.Log
import retrofit2.Response
import java.lang.Exception
import java.net.HttpURLConnection

sealed class NetworkResult<out T> {
    data class Success<T> (val value : T): NetworkResult<T>()
    data class Error(val statusCode: Int?, val message: String?): NetworkResult<Nothing>()
}

fun <T : Any> Response<T>.parseResponse(): NetworkResult<T> {
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