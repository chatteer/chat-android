package com.chatteer.feature.friend.usecase

import com.chatteer.feature.friend.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Description :
 *
 * Created by juhongmin on 2024. 10. 3.
 */
class FriendUseCase @Inject constructor(
    private val apiService: ApiService
) {
    operator fun invoke() : Flow<List<String>>{
        return flow {
            emit(listOf<String>())
        }.flowOn(Dispatchers.IO)
    }
}