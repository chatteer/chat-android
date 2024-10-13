package com.chatteer.core.ui

import androidx.annotation.ColorInt
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Description : 필요에 따라 상태바 색상 변경하는 클래스
 *
 * Created by juhongmin on 2024. 10. 12.
 */
object StatusBarChangeEvent {
    private val _events: MutableSharedFlow<StatusBar> by lazy {
        MutableSharedFlow(replay = 1)
    }
    val events: SharedFlow<StatusBar> = _events.asSharedFlow()

    suspend fun send(
        @ColorInt color: Int
    ) {
        send(StatusBar(color = color))
    }

    suspend fun send(event: StatusBar) {
        _events.emit(event)
    }
}

data class StatusBar(
    @ColorInt val color: Int = 0xFFFFFF,
    val isHidden: Boolean = false
)
