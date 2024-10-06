package com.chatteer.feature.friend.model.ui

import com.chatteer.core.model.MemberData
import com.chatteer.core.ui.BaseUiModel

/**
 * Description : 친구 목록 메인 화면 UiModel
 *
 * Created by juhongmin on 2024. 10. 6.
 */
sealed interface FriendMainModel {

    data class Error(
        val msg: String
    ) : FriendMainModel

    data class Success(
        val member: MemberData,
        val friendUiList: List<BaseUiModel>
    ) : FriendMainModel
}
