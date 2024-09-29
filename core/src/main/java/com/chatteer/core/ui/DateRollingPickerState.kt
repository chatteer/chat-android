package com.chatteer.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.time.LocalDateTime

/**
 * Description : DateRollingPicker 전용 State
 * @see ChatComponents.CustomDateRollingPicker
 *
 * Created by juhongmin on 2024. 9. 29.
 */
class DateRollingPickerState {
    var currentDate by mutableStateOf(LocalDateTime.now())
}

@Composable
fun rememberDateRollingPickerState() = remember { DateRollingPickerState() }
