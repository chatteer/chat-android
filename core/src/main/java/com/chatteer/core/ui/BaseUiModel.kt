package com.chatteer.core.ui

import androidx.compose.runtime.Composable

/**
 * Description :
 *
 * Created by juhongmin on 2024. 10. 3.
 */
interface BaseUiModel {
    fun getType(): String

    @Composable
    fun GetUi(clickEvent: (BaseClickEvent) -> Unit)
}