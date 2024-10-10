package com.chatteer.feature.friend.model.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chatteer.core.R
import com.chatteer.core.model.FriendData
import com.chatteer.core.model.MemberData
import com.chatteer.core.ui.BaseClickEvent
import com.chatteer.core.ui.BaseUiModel
import com.chatteer.core.ui.ChatComponents.BoxPadding
import com.chatteer.core.ui.ChatComponents.ImageLoader
import com.chatteer.core.ui.ChatTheme
import com.chatteer.feature.friend.event.ChatRoomEvent
import timber.log.Timber

/**
 * Description : 친구 목록
 *
 * Created by juhongmin on 2024. 10. 3.
 */
data class FriendUiModel(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val userId: Long,
    val chatRoomEvent: ChatRoomEvent
) : BaseUiModel {

    constructor(
        member: MemberData,
        data: FriendData
    ) : this(
        id = data.id,
        name = data.name,
        imageUrl = data.profileImageUrl,
        userId = member.id,
        chatRoomEvent = ChatRoomEvent(
            ownerId = member.id,
            friendIds = listOf(data.id)
        )
    )

    override fun getType(): String {
        return "FriendUiModel"
    }

    @Composable
    override fun GetUi(clickEvent: (BaseClickEvent) -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 5.dp)
                .background(ChatTheme.color.navigation, RoundedCornerShape(8.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
                    .padding(vertical = 10.dp, horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImageLoader(
                    imageUrl = imageUrl,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(1.dp, ChatTheme.color.white, CircleShape)
                )

                Column(
                    modifier = Modifier
                        .weight(1F)
                        .padding(start = 5.dp, end = 5.dp)
                ) {
                    Text(
                        text = name,
                        style = ChatTheme.text.h4M,
                        color = ChatTheme.color.black
                    )
                    Text(
                        text = "오늘의 한마디...",
                        style = ChatTheme.text.h5,
                        color = ChatTheme.color.gray1
                    )
                }

                BoxPadding(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .clickable { clickEvent(chatRoomEvent) }
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_navigation_chat_selected),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(15.dp),
                        colorFilter = ColorFilter.tint(ChatTheme.color.primary)
                    )
                }
            }
        }

    }
}