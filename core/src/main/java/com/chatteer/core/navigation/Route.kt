package com.chatteer.core.navigation

import kotlinx.serialization.Serializable

/**
 * Description : 화면 Router
 *
 * Created by juhongmin on 2024. 9. 25.
 */
sealed interface Route {

}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Friend : MainTabRoute

    @Serializable
    data object Chat : MainTabRoute

    @Serializable
    data object More : MainTabRoute
}