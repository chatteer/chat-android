package com.chatteer.core.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.chatteer.core.R

/**
 * Description : Compose TextStyle
 *
 * Created by juhongmin on 2024. 9. 17.
 */
@Immutable
@Suppress("unused", "MemberVisibilityCanBePrivate")
class ChatTextStyle internal constructor(
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
){
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