package com.chatteer.feature.friend.usecase

import com.chatteer.core.data.remote.models.BaseJSend
import com.chatteer.core.data.remote.models.JSendException
import com.chatteer.core.data.remote.repository.FriendRepository
import com.chatteer.core.data.remote.repository.MemberRepository
import com.chatteer.core.ui.BaseUiModel
import com.chatteer.feature.friend.model.ui.FriendMainModel
import com.chatteer.feature.friend.model.ui.FriendUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Description :
 *
 * Created by juhongmin on 2024. 10. 3.
 */
class FriendUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
    private val friendRepository: FriendRepository
) {
    operator fun invoke(): Flow<FriendMainModel> {
        return combine(
            flow { emit(memberRepository.fetch()) },
            fetchFriendList()
        ) { memberResponse, friendList ->
            val result: FriendMainModel = try {
                val member = memberResponse.getOrThrow()
                FriendMainModel.Success(
                    member = member,
                    friendUiList = friendList
                )
            } catch (ex: Exception) {
                if (ex is JSendException.Network) {
                    FriendMainModel.Error("네트워크 에러 입니다.\n잠시후 다시 이용해주세요.")
                } else if (ex is JSendException.JSendResponse) {
                    val msg = ex.getErrorBody<BaseJSend>()?.message
                    FriendMainModel.Error(msg ?: "잠시후 다시 이용 해주세요.")
                } else {
                    FriendMainModel.Error("잠시후 다시 이용 해주세요.")
                }
            }
            return@combine result
        }.flowOn(Dispatchers.IO)
    }

    private fun fetchFriendList(): Flow<List<BaseUiModel>> {
        return flow {
            val res = friendRepository.fetch()
            emit(res.map { FriendUiModel(it) })
        }.flowOn(Dispatchers.IO)
    }
}