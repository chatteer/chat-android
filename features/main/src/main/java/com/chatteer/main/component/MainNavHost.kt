package com.chatteer.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chatteer.core.navigation.MainTabRoute
import com.chatteer.core.ui.ChatTheme
import com.chatteer.main.MainNavigator
import com.chatteer.main.MainTab
import com.chatteer.main.MainViewModel

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    viewModel: MainViewModel
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ChatTheme.color.white)
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = MainTab.Friend.route
        ) {
            composable<MainTabRoute.Friend> {
                Box {
                    Text("친구")
                }
            }
            composable<MainTabRoute.Chat> {
                Box {
                    Text("채팅")
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

