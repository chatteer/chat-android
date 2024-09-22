package com.chatteer.core.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chatteer.core.R

/**
 * Description : 채티어 테마 예시
 *
 * Created by juhongmin on 2024. 9. 17.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun HeaderType1() {
    var isHeaderHide = false
    val scrollState = rememberScrollState()
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val dy = available.y
                println("Scroll $dy")
                return if (isHeaderHide) {
                    Offset.Zero
                } else if (dy < 0) {
                    // 터치가 아래에서 위로, 화면을 위로 올릴때 부모가 먼저 스크롤 받기
                    scrollState.dispatchRawDelta(dy * -1)
                    Offset(0f, dy)
                } else {
                    // 위에서 아래로 내릴때 부모가 스크롤 이벤트 받지 않도록
                    Offset.Zero
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(ChatTheme.color.primary)
            ) {
                Text(
                    text = "친구",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 10.dp),
                    color = ChatTheme.color.white,
                    style = ChatTheme.text.h3M
                )
                Image(
                    painter = painterResource(R.drawable.ic_add_chat),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 10.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            var globalHeight by remember { mutableStateOf(0) }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .onSizeChanged { size ->
                        globalHeight = size.height
                    }
                    .nestedScroll(nestedScrollConnection)
                    .verticalScroll(scrollState)
            ) {
                // Header
                HeaderContents(
                    header = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(ChatTheme.color.primary)
                                .height(200.dp)
                        ) {
                            Text(
                                text = "여기가 헤더입니까?",
                                modifier = Modifier.padding(20.dp)
                            )
                        }
                    },
                    onHide = { isHide ->
                        isHeaderHide = isHide
                    }
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(globalHeight.dp)
                ) {
                    items(50) { index ->
                        Text(
                            text = "Item $index",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderContents(
    header: @Composable ColumnScope.() -> Unit,
    onHide: (Boolean) -> Unit
) {
    var contentHeight by remember { mutableStateOf(0) }
    var visiblePercentage by remember { mutableStateOf(1f) }

    LaunchedEffect(visiblePercentage) {
        if (visiblePercentage <= 0f) {
            onHide(true)
        } else {
            onHide(false)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .onGloballyPositioned { layoutCoordinates ->
                visiblePercentage = layoutCoordinates.boundsInRoot().height / contentHeight
            }
            .onSizeChanged { contentHeight = it.height }
            .alpha(visiblePercentage)
    ) { this.header() }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
private fun HeaderExample1() {
    val lazyScrollState = rememberLazyListState()
    Scaffold {
        ChatComponents.HeaderAndContents(
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
            }
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
            }
        }
    }
}