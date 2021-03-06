package com.example.mvptest.model

import androidx.annotation.NonNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestApi{
    companion object {
        private val URL = "https://chat.pryaniky.com/"
        private val TIMEOUT_IN_SECONDS = 5
        private var sRestApi: RestApi? = null

        fun getInstance(): RestApi?{
            if (sRestApi == null) {
                sRestApi = RestApi()
            }
            return sRestApi
        }
    }

    private val dataEndpoint: DataEndpoint

    @NonNull
    private fun buildRetrofitClient(@NonNull client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @NonNull
    private fun buildOkHttpClient(): OkHttpClient {
        val networkLogInterceptor = HttpLoggingInterceptor()
        networkLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

        return OkHttpClient.Builder()
            .addInterceptor(networkLogInterceptor)
            .connectTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .build()
    }

    init {
        val httpClient = buildOkHttpClient()
        val retrofit = buildRetrofitClient(httpClient)
        dataEndpoint = retrofit.create(DataEndpoint::class.java)
    }
    fun data(): DataEndpoint = dataEndpoint
}
