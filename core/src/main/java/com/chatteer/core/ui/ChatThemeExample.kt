package com.chatteer.core.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chatteer.core.R
import com.chatteer.core.ui.ChatComponents.CustomText
import com.chatteer.core.ui.ChatComponents.HeaderAndContents
import com.chatteer.core.ui.ChatComponents.TextFieldTextType
import com.chatteer.core.ui.ChatComponents.VerticalSpace

/**
 * Description : 채티어 테마 예시
 *
 * Created by juhongmin on 2024. 9. 17.
 */
@Composable
@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
private fun HeaderExample1() {
    val lazyScrollState = rememberLazyListState()
    Scaffold {
        HeaderAndContents(
            navigationHeader = {
                Text(
                    text = "친구",
                    style = ChatTheme.text.h,
                    color = ChatTheme.color.white,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 15.dp)
                )
                Image(
                    painter = painterResource(R.drawable.ic_add_chat),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 15.dp)
                )
            },
            collapseHeader = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(ChatTheme.color.primary)
                ) {
                    ChatComponents.ImageLoader(
                        imageUrl = "https://til.qtzz.synology.me/resources/img/20210921/1632238064795dwalkkz7dea.png",
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.Center)
                            .clip(CircleShape)
                            .border(2.dp, ChatTheme.color.white, CircleShape)
                    )
                }
            },
            stickyHeader = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(ChatTheme.color.gray3),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "StickyHeader...",
                        style = ChatTheme.text.h
                    )
                }
            },
            scrollState = lazyScrollState
        ) {
            LazyColumn(
                state = lazyScrollState
            ) {
                items(100) { index ->
                    Text(
                        text = "Item $index",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
                item {
                    VerticalSpace(100)
                }
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
private fun CustomTextExample() {
    val scrollState = rememberScrollState()
    val isTextEnable = remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        CustomText(
            text = "일반 텍스트",
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(ChatTheme.color.white, RoundedCornerShape(6.dp))
                .border(1.dp, ChatTheme.color.black, RoundedCornerShape(6.dp))
        )
        CustomText(
            text = "활/비활성화 텍스트",
            isEnable = isTextEnable.value,
            enableModifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(ChatTheme.color.primary, RoundedCornerShape(6.dp)),
            disableModifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(ChatTheme.color.gray3, RoundedCornerShape(6.dp))
        ) {
            isTextEnable.value = !isTextEnable.value
        }

        TextFieldTextType(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(ChatTheme.color.white, RoundedCornerShape(6.dp))
                .border(1.dp, ChatTheme.color.black, RoundedCornerShape(6.dp)),
            placeHolder = {
                Text(
                    text = "입력하세요.",
                    style = ChatTheme.text.h4M,
                    color = ChatTheme.color.black
                )
            },
            imeAction = ImeAction.Done,
            callback = {
                println("Timber 실시간 $it")
            }
        ) { confirmText ->
            println("Timber 확인 버튼 $confirmText")
        }
    }
}