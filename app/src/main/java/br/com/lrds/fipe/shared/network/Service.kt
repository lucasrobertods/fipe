package br.com.lrds.fipe.shared.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Service {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL + API_VERSION)
        .client(OkHttpClient.Builder().addInterceptor(logInterceptor()).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <API> createService(apiClass: Class<API>): API {
        return retrofit.create(apiClass)
    }

    private fun logInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    companion object {
        private const val BASE_URL = "https://parallelum.com.br/fipe/api/"
        private const val API_VERSION = "v1/"
    }

}