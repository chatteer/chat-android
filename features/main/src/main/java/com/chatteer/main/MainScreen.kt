package com.chatteer.main

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import com.chatteer.main.component.MainBottomBar
import com.chatteer.main.component.MainNavHost
import kotlinx.collections.immutable.toPersistentList

/**
 * Description : 메인 화면
 *
 * Created by juhongmin on 2024. 9. 25.
 */
@Composable
internal fun MainScreen(
    navigator: MainNavigator,
    viewModel: MainViewModel
) {
    val focusManager = LocalFocusManager.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        content = {
            MainNavHost(
                navigator = navigator,
                paddingValues = it,
                viewModel = viewModel
            )
        },
        bottomBar = {
            MainBottomBar(
                tabs = MainTab.entries.toPersistentList(),
                currentTab = navigator.currentTab,
                onSelected = { navigator.navigate(it) },
                onReselected = { navigator.onScrollTop(it) }
            )
        }
    )
}