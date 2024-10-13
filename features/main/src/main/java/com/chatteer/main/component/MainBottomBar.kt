package com.chatteer.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.chatteer.core.ui.ChatTheme
import com.chatteer.main.MainTab
import kotlinx.collections.immutable.PersistentList

@Composable
internal fun MainBottomBar(
    modifier: Modifier = Modifier,
    tabs: PersistentList<MainTab>,
    visible: Boolean,
    currentTab: MainTab?,
    onSelected: (MainTab) -> Unit,
    onReselected: (MainTab) -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) }
    ) {
        Row(modifier = modifier) {
            tabs.forEach { tab ->
                MainBottomBarItem(
                    modifier = Modifier.weight(1f),
                    item = tab,
                    selected = tab == currentTab,
                    onClick = {
                        if (currentTab == tab) {
                            onReselected(tab)
                        } else {
                            onSelected(tab)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun MainBottomBarItem(
    modifier: Modifier = Modifier,
    item: MainTab,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .selectable(
                selected = selected,
                indication = null,
                role = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val iconId = if (selected) item.selectedIconId else item.notSelectedIconId
        Image(
            painter = painterResource(iconId),
            contentDescription = item.contentDescription,
            modifier = Modifier.padding(vertical = 6.dp)
        )
        Text(
            text = item.contentDescription,
            style = ChatTheme.text.h5M,
            modifier = Modifier.padding(bottom = 10.dp)
        )
    }
}