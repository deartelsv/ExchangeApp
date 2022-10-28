package ru.artelsv.exchangeapp.utils

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.artelsv.exchangeapp.BuildConfig


class DefaultInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            .header(API_KEY, BuildConfig.API_KEY)
            .build()

        return chain.proceed(request)
    }

    companion object {

        private const val API_KEY = "apikey"
    }
}