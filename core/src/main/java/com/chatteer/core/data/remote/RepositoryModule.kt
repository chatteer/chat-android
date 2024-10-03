package com.chatteer.core.data.remote

import com.chatteer.core.data.remote.impl.FriendRepositoryImpl
import com.chatteer.core.data.remote.impl.MemberRepositoryImpl
import com.chatteer.core.data.remote.repository.FriendRepository
import com.chatteer.core.data.remote.repository.MemberRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [RemoteModule::class])
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMemberRepository(impl: MemberRepositoryImpl): MemberRepository

    @Binds
    @Singleton
    abstract fun bindFriendRepository(impl: FriendRepositoryImpl): FriendRepository
}