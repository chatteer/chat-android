package com.chatteer.feature.friend.usecase

import com.chatteer.core.data.remote.models.BaseJSend
import com.chatteer.core.data.remote.models.JSendException
import com.chatteer.core.data.remote.repository.FriendRepository
import com.chatteer.core.data.remote.repository.MemberRepository
import com.chatteer.feature.friend.model.ui.FriendMainModel
import com.chatteer.feature.friend.model.ui.FriendMainModel.Error
import com.chatteer.feature.friend.model.ui.FriendMainModel.Success
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
            flow { emit(friendRepository.fetch()) }
        ) { memberResponse, friendList ->
            val result: FriendMainModel = try {
                val member = memberResponse.getOrThrow()
                Success(
                    member = member,
                    uiList = friendList.map { FriendUiModel(member, it) }
                )
            } catch (ex: Exception) {
                when (ex) {
                    is JSendException.Network -> {
                        Error("네트워크 에러 입니다.\n잠시후 다시 이용해주세요.")
                    }

                    is JSendException.JSendResponse -> {
                        val msg = ex.getErrorBody<BaseJSend>()?.message
                        Error(msg ?: "잠시후 다시 이용 해주세요.")
                    }

                    else -> {
                        Error("잠시후 다시 이용 해주세요.")
                    }
                }
            }
            return@combine result
        }.flowOn(Dispatchers.IO)
    }
}
