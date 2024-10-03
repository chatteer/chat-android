package com.chatteer.core.data.tcp.remote.impl

import android.content.Context
import com.chatteer.core.R
import com.chatteer.core.data.tcp.remote.CoroutineErrorHandlingCallAdapter
import com.chatteer.core.data.tcp.remote.NetworkProvider
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

/**
 * Description : Network Provider 구현체 클래스
 *
 * Created by juhongmin on 2024. 9. 16.
 */
internal class NetworkProviderImpl(
    context: Context,
    json: Json,
    httpClient: OkHttpClient
) : NetworkProvider {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(context.getString(R.string.BaseUrl))
            .client(httpClient)
            .addCallAdapterFactory(CoroutineErrorHandlingCallAdapter.create())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    override fun <T> createApi(service: Class<T>): T {
        return retrofit.create(service)
    }
}