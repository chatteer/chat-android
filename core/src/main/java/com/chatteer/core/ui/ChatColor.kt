package com.chatteer.core.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Description : 채티어 색상
 *
 * Created by juhongmin on 2024. 9. 17.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
@Immutable
class ChatColor internal constructor(
    val white: Color,
    val black: Color,
    val primary: Color,
    val red: Color,
    val gray1: Color,
    val gray2: Color,
    val gray3: Color,
    val gray4: Color,
    val gray5: Color,
    val navigation: Color
) {
    constructor() : this(
        white = Color(0xFFFFFFFF),
        black = Color(0xFF222222),
        primary = Color(32, 146, 196),
        red = Color(0xFFD7402B),
        gray1 = Color(0xFF586375),
        gray2 = Color(0xFF77808F),
        gray3 = Color(0xFFBABEC3),
        gray4 = Color(0xFFE5E7EA),
        gray5 = Color(0xFFEEEEEE),
        navigation = Color(243, 245, 244)
    )
}