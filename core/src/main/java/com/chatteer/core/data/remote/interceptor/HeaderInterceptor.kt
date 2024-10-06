package com.chatteer.core.data.remote.interceptor

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Description : API Header Interceptor
 *
 * Created by juhongmin on 2024. 10. 6.
 */
internal class HeaderInterceptor(
    private val preferences: SharedPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val reqBuilder = chain.request().newBuilder()

        val token = preferences.getString("auth_access_token","")
        if (!token.isNullOrEmpty()) {
            reqBuilder.header("Authorization", token)
        }
        return chain.proceed(reqBuilder.build())
    }
}