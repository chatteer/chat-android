package com.chatteer.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp

@Composable
fun measureTextWidth(text: String, style: TextStyle): Dp {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current

    return remember(text, style.fontSize) {
        val measure = textMeasurer.measure(text = text, style = style)
        with(density) { measure.size.width.toDp() }
    }
}
