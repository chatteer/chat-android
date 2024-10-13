package com.chatteer.feature.friend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.chatteer.core.ui.ChatComponents.BoxPadding
import com.chatteer.core.ui.ChatComponents.HeaderAndContents
import com.chatteer.core.ui.ChatComponents.HorizontalSpace
import com.chatteer.core.ui.ChatComponents.ImageLoader
import com.chatteer.core.ui.ChatComponents.LoadingDialog
import com.chatteer.core.ui.ChatTheme
import com.chatteer.core.ui.StatusBarChangeEvent
import com.chatteer.feature.friend.event.ChatRoomEvent
import com.chatteer.feature.friend.model.ui.FriendMainModel
import timber.log.Timber

/**
 * Description : 친구 목록
 *
 * Created by juhongmin on 2024. 10. 1.
 */
@Composable
fun FriendScreen(
    navBackStackEntry: NavBackStackEntry,
    viewModel: FriendViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Box(
        modifier = Modifier
    ) {
        when (val data = uiState) {
            is FriendMainModel.Loading -> {
                LoadingDialog()
            }

            is FriendMainModel.Success -> {
                SuccessUi(data, viewModel)
            }

            is FriendMainModel.Error -> {
                // 네트워크 에러 발생

            }
        }
    }

    val statusColor = ChatTheme.color.primary.toArgb()
    LaunchedEffect(Unit) {
        StatusBarChangeEvent.send(statusColor)
    }
}

@Composable
private fun SuccessUi(
    data: FriendMainModel.Success,
    viewModel: FriendViewModel
) {
    val scrollState = rememberLazyListState()
    HeaderAndContents(
        navigationHeader = {
            Text(
                text = "친구",
                style = ChatTheme.text.h3,
                modifier = Modifier
                    .padding(start = 15.dp)
                    .align(Alignment.CenterStart),
                color = ChatTheme.color.white
            )
            BoxPadding(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                        // 다중 채팅 화면 이동
                    }
            ) {
                Image(
                    painter = painterResource(com.chatteer.core.R.drawable.ic_add_chat),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .fillMaxHeight()
                )
            }
        },
        collapseHeader = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(ChatTheme.color.primary)
                    .align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalSpace(6)
                ImageLoader(
                    imageUrl = data.member.profileImageUrl,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(1.dp, ChatTheme.color.white, CircleShape)
                )
                HorizontalSpace(6)
                Text(
                    text = data.member.name,
                    style = ChatTheme.text.h3M,
                    color = ChatTheme.color.white
                )
            }
        },
        scrollState = scrollState
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState,
            contentPadding = PaddingValues(bottom = 56.dp)
        ) {
            itemsIndexed(
                items = data.uiList,
                key = { idx, _ -> idx },
                contentType = { _, item -> item.getType() },
            ) { _, item ->
                item.GetUi { event ->
                    if (event is ChatRoomEvent) {
                        Timber.d("방 생성 $event")
                    }
                }
            }
        }
    }
}
