package com.chatteer.main

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.chatteer.core.R
import com.chatteer.core.navigation.MainTabRoute
import com.chatteer.core.navigation.Route

/**
 * Description : 메인 탭
 *
 * Created by juhongmin on 2024. 9. 25.
 */
enum class MainTab(
    @DrawableRes val selectedIconId: Int,
    @DrawableRes val notSelectedIconId: Int,
    val contentDescription: String,
    val route: MainTabRoute
) {
    Friend(
        selectedIconId = R.drawable.ic_navigation_friend_selected,
        notSelectedIconId = R.drawable.ic_navigation_friend_not_selected,
        contentDescription = "친구",
        route = MainTabRoute.Friend
    ),
    Chat(
        selectedIconId = R.drawable.ic_navigation_chat_selected,
        notSelectedIconId = R.drawable.ic_navigation_chat_not_selected,
        contentDescription = "채팅",
        route = MainTabRoute.Chat
    ),
    More(
        selectedIconId = R.drawable.ic_navigation_more_selected,
        notSelectedIconId = R.drawable.ic_navigation_more_not_selected,
        contentDescription = "더보기",
        route = MainTabRoute.More

    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}