package com.refresh.enter.abel.buchi.api

import android.os.Build
import com.refresh.enter.abel.buchi.constant.Url.CALL_TIMEOUT
import com.refresh.enter.abel.buchi.constant.Url.CONNECT_TIMEOUT
import com.refresh.enter.abel.buchi.constant.Url.READ_TIMEOUT
import com.refresh.enter.abel.buchi.constant.Url.URL_BASE
import com.refresh.enter.abel.buchi.constant.Url.WRITE_TIMEOUT
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object ApiWorker {
    // Create logger
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // Create OkHttp Client
    private val httpBuilder = OkHttpClient.Builder()
        .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .callTimeout(CALL_TIMEOUT.toLong(), TimeUnit.MINUTES)
        .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .addInterceptor(Interceptor { chain: Interceptor.Chain ->
            var request = chain.request()
            request = request.newBuilder()
                .addHeader("x-device-type", Build.DEVICE)
                .addHeader("Accept-Language", Locale.getDefault().language)
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(request)
        })
        .addInterceptor(logger)
    private val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpBuilder.build())
    private val retrofit = builder.build()
    fun  buildService(serviceType: Class<*>?): Any? {
        return retrofit.create(serviceType)
    }
}