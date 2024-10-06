package com.chatteer.feature.friend.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.chatteer.core.navigation.MainTabRoute
import com.chatteer.feature.friend.FriendScreen

fun NavController.navigateFriend(navOptions: NavOptions) {
    navigate(MainTabRoute.Friend, navOptions)
}

fun NavGraphBuilder.friendNavGraph() {
    composable<MainTabRoute.Friend>() {
        FriendScreen(it)
    }
}