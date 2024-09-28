package com.chatteer.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.chatteer.core.navigation.MainTabRoute

/**
 * Description : 메인 네비게이션
 * 드로이드 나이츠 참고
 * Created by juhongmin on 2024. 9. 25.
 */
internal class MainNavigator(
    val navController: NavHostController
) {

    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (tab) {
            MainTab.Friend -> {
                // friend
                navController.navigate(MainTabRoute.Friend, navOptions)
            }

            MainTab.Chat -> {
                // chat
                navController.navigate(MainTabRoute.Chat, navOptions)
            }

            MainTab.More -> {
                // more
                navController.navigate(MainTabRoute.More, navOptions)
            }
        }
    }

    fun onScrollTop(tab: MainTab) {

    }

    @Composable
    fun shouldShowBottomBar() = MainTab.contains {
        currentDestination?.hasRoute(it::class) == true
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
