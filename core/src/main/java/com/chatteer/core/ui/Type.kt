package com.chatteer.core.ui

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.chatteer.core.R

@Immutable
data class ChatTypography(
    val h: TextStyle,
    val hM: TextStyle,
    val hB: TextStyle,
    val h1: TextStyle,
    val h1M: TextStyle,
    val h1B: TextStyle,
    val h2: TextStyle,
    val h2M: TextStyle,
    val h2B: TextStyle,
    val h3: TextStyle,
    val h3M: TextStyle,
    val h3B: TextStyle,
    val h4: TextStyle,
    val h4M: TextStyle,
    val h4B: TextStyle,
    val h5: TextStyle,
    val h5M: TextStyle,
    val h5B: TextStyle
) {

    companion object {
        private fun TextStyle.changeStyle(default: FontFamily): TextStyle {
            return if (fontFamily != null) this else copy(fontFamily = default)
        }
    }

    constructor(
        regularFont: FontFamily = FontFamily(Font(R.font.pretendard_regular)),
        mediumFont: FontFamily = FontFamily(Font(R.font.pretendard_medium)),
        boldFont: FontFamily = FontFamily(Font(R.font.pretendard_semibold)),
        h: TextStyle = TextStyle(fontSize = 32.sp),
        h1: TextStyle = TextStyle(fontSize = 28.sp),
        h2: TextStyle = TextStyle(fontSize = 24.sp),
        h3: TextStyle = TextStyle(fontSize = 20.sp),
        h4: TextStyle = TextStyle(fontSize = 16.sp),
        h5: TextStyle = TextStyle(fontSize = 12.sp)
    ) : this(
        h = h.changeStyle(regularFont),
        hM = h.changeStyle(mediumFont),
        hB = h.changeStyle(boldFont),
        h1 = h1.changeStyle(regularFont),
        h1M = h1.changeStyle(mediumFont),
        h1B = h1.changeStyle(boldFont),
        h2 = h2.changeStyle(regularFont),
        h2M = h2.changeStyle(mediumFont),
        h2B = h2.changeStyle(boldFont),
        h3 = h3.changeStyle(regularFont),
        h3M = h3.changeStyle(mediumFont),
        h3B = h3.changeStyle(boldFont),
        h4 = h4.changeStyle(regularFont),
        h4M = h4.changeStyle(mediumFont),
        h4B = h4.changeStyle(boldFont),
        h5 = h5.changeStyle(regularFont),
        h5M = h5.changeStyle(mediumFont),
        h5B = h5.changeStyle(boldFont)
    )
}

@Immutable
data class ChatColor(
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

internal val LocalTypography = staticCompositionLocalOf { ChatTypography() }
internal val Typography = ChatTypography()
internal val LocalColor = staticCompositionLocalOf { ChatColor() }
internal val Color = ChatColor()