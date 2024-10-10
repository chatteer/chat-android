package com.chatteer.feature.friend.event

import com.chatteer.core.ui.BaseClickEvent

/**
 * Description : 채팅방 만들기 이벤트
 * @param ownerId 방장 사용자 아이디
 * @param friendIds 초대할 친구들 아이디
 *
 * Created by juhongmin on 2024. 10. 10.
 */
data class ChatRoomEvent(
    val ownerId: Long,
    val friendIds: List<Long> = listOf()
) : BaseClickEvent