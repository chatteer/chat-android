package com.chatteer.main.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chatteer.core.navigation.MainTabRoute
import com.chatteer.core.ui.ChatComponents.BoxPadding
import com.chatteer.core.ui.ChatComponents.CustomText
import com.chatteer.core.ui.ChatTheme
import com.chatteer.core.ui.StatusBar
import com.chatteer.core.ui.StatusBarChangeEvent
import com.chatteer.feature.friend.navigation.friendNavGraph
import com.chatteer.main.MainNavigator
import com.chatteer.main.MainTab
import com.chatteer.main.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    viewModel: MainViewModel
) {
    Box(modifier = modifier) {
        NavHost(
            navController = navigator.navController,
            startDestination = MainTab.Friend.route
        ) {
            friendNavGraph()
            composable<MainTabRoute.Chat> {
                val statusColor = ChatTheme.color.black.toArgb()
                val list = (0..40)
                    .map { "$it" }
                    .toList()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ChatTheme.color.gray2)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(top = 30.dp)
                    ) {
                        stickyHeader(
                            key = "header",
                        ) {
                            BoxPadding(
                                modifier = Modifier.fillMaxWidth()
                                    .statusBarsPadding()
                                    .padding(horizontal = 20.dp)
                            ) {
                                Text("채팅")
                            }
                        }
                        itemsIndexed(
                            items = list
                        ) { index, item ->
                            CustomText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .background(ChatTheme.color.white, RoundedCornerShape(6.dp)),
                                text = item
                            )
                        }
                    }
                }
                LaunchedEffect(Unit) {
                    StatusBarChangeEvent.send(
                        StatusBar(
                            statusColor, true
                        )
                    )
                }
            }
            composable<MainTabRoute.More> {
                Box {
                    Text("더보기")
                }
            }
        }
    }
}

