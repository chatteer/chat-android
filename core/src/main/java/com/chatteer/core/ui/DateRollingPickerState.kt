package com.chatteer.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.time.LocalDateTime

/**
 * Description : DateRollingPicker 전용 State
 * @see ChatComponents.DateRollingPicker
 *
 * Created by juhongmin on 2024. 9. 29.
 */
class DateRollingPickerState {

    var currentDate: LocalDateTime by mutableStateOf(LocalDateTime.now())

    fun update(newDate: LocalDateTime) {
        currentDate = newDate
    }
}

@Composable
fun rememberDateRollingPickerState() = remember { DateRollingPickerState() }
