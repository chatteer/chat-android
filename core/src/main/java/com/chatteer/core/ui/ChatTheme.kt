package com.chatteer.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Description : 채티어 테마
 *
 * Created by juhongmin on 2024. 9. 17.
 */
object ChatTheme {

    private val _textStyle = staticCompositionLocalOf { ChatTextStyle() }
    private val _color = staticCompositionLocalOf { ChatColor() }

    val text: ChatTextStyle
        @Composable
        @ReadOnlyComposable
        get() = _textStyle.current
    val color: ChatColor
        @Composable
        @ReadOnlyComposable
        get() = _color.current
}
