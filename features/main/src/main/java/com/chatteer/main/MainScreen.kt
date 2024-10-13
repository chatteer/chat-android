package com.chatteer.main

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chatteer.core.ui.ChatComponents.drawTopBorder
import com.chatteer.core.ui.ChatTheme
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
    val window = (LocalContext.current as Activity).window
    val statusBar by viewModel.statusBar.collectAsStateWithLifecycle()

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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                navigator = navigator,
                viewModel = viewModel
            )
        },
        bottomBar = {
            MainBottomBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .background(ChatTheme.color.navigation)
                    .drawTopBorder(ChatTheme.color.gray3, 1.dp),
                tabs = MainTab.entries.toPersistentList(),
                visible = navigator.shouldShowBottomBar(),
                currentTab = navigator.currentTab,
                onSelected = { navigator.navigate(it) },
                onReselected = { navigator.onScrollTop(it) }
            )
        },
        contentWindowInsets = if (statusBar.isHidden) {
            WindowInsets.navigationBars
        } else {
            WindowInsets.systemBars
        }
    )

    LaunchedEffect(statusBar.color) {
        if (statusBar.isHidden) {
            window.statusBarColor = 0x00000000
        } else {
            window.statusBarColor = statusBar.color
        }
    }
}