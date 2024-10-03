package com.chatteer.feature.friend

import com.chatteer.core.data.tcp.remote.NetworkProvider
import com.chatteer.feature.friend.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object FeatureModule {

    @Provides
    fun provideApiService(
        provider: NetworkProvider
    ): ApiService = provider.createApi(ApiService::class.java)
}
