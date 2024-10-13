package com.chatteer.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

/**
 * Description : 채티어 테마
 *
 * Created by juhongmin on 2024. 9. 17.
 */

object ChatTheme {

    val text: ChatTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
    val color: ChatColor
        @Composable
        @ReadOnlyComposable
        get() = LocalColor.current
}

private val LightColorScheme = lightColorScheme(
    primary = Color.primary,
    onPrimary = Color.white,
    primaryContainer = Color.white,
    background = Color.navigation
)
private val DarkColorScheme = darkColorScheme(

)

@Composable
fun ChatTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColor provides Color,
        LocalTypography provides Typography
    ) {
        MaterialTheme(
            colorScheme = LightColorScheme,
            content = content
        )
    }
}
