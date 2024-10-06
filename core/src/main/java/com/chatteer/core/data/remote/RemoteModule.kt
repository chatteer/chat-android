package com.chatteer.core.data.remote

import android.content.Context
import androidx.core.content.edit
import com.chatteer.core.R
import com.chatteer.core.data.remote.apis.FriendApiService
import com.chatteer.core.data.remote.apis.MemberApiService
import com.chatteer.core.data.remote.fake.FakeRepository
import com.chatteer.core.data.remote.interceptor.HeaderInterceptor
import com.chatteer.core.data.remote.qualifier.ApiHeaderInterceptor
import com.chatteer.core.data.remote.qualifier.TrackingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hmju.http.tracking_interceptor.TrackingHttpInterceptor
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by juhongmin on 2024. 9. 16.
 */
@Module
@InstallIn(SingletonComponent::class)
internal object RemoteModule {
    @Singleton
    @Provides
    fun provideJsonFormat(): Json = Json {
        isLenient = true // Json 큰따옴표 느슨하게 체크.
        ignoreUnknownKeys = true // Field 값이 없는 경우 무시
        coerceInputValues = true // "null" 이 들어간경우 default Argument 값으로 대체
        encodeDefaults = true // 직렬화시 기본값 포함해서 셋팅
    }

    @Singleton
    @Provides
    @TrackingInterceptor
    fun provideTrackingInterceptor(): Interceptor = TrackingHttpInterceptor()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor(
        logger = { Timber.tag("HTTP_LOG").d(it) }
    ).apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    @ApiHeaderInterceptor
    fun provideApiHeaderInterceptor(
        @ApplicationContext context: Context
    ): Interceptor {
        val pref = context.getSharedPreferences("chatter_pref", Context.MODE_PRIVATE)
        pref.edit {
            putString("auth_access_token", context.getString(R.string.test_token))
        }
        return HeaderInterceptor(pref)
    }

    @Singleton
    @Provides
    fun provideCallAdapter(): CallAdapter.Factory {
        return CoroutineErrorHandlingCallAdapter.create()
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        @ApplicationContext context: Context,
        @TrackingInterceptor trackingInterceptor: Interceptor,
        @ApiHeaderInterceptor headerInterceptor: Interceptor,
        httpLogInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .callTimeout(60, TimeUnit.SECONDS)
        builder.addInterceptor(headerInterceptor)
        if (context.getString(R.string.BuildType) != "release") {
            builder.addInterceptor(trackingInterceptor)
            builder.addInterceptor(httpLogInterceptor)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        @ApplicationContext context: Context,
        json: Json,
        client: OkHttpClient
    ): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(context.getString(R.string.BaseUrl))
            .client(client)
            .addCallAdapterFactory(CoroutineErrorHandlingCallAdapter.create())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideFriendApi(
        retrofit: Retrofit
    ): FriendApiService {
        return retrofit.create()
    }

    @Singleton
    @Provides
    fun provideMemberApi(
        retrofit: Retrofit
    ): MemberApiService {
        return retrofit.create()
    }

    @Singleton
    @Provides
    fun provideFakeRepository(
        @ApplicationContext context: Context,
        json: Json,
        httpClient: OkHttpClient
    ): FakeRepository {
        return FakeRepository(context, json, httpClient)
    }
}