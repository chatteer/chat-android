package com.chatteer.feature.friend.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.chatteer.core.navigation.MainTabRoute
import com.chatteer.feature.friend.FriendScreen


fun NavGraphBuilder.friendNavGraph(

) {
    composable<MainTabRoute.Friend>() {
        FriendScreen(it,)
    }
}