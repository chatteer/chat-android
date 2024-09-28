package com.chatteer.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chatteer.main.MainTab
import kotlinx.collections.immutable.PersistentList
import timber.log.Timber

@Composable
internal fun MainBottomBar(
    tabs: PersistentList<MainTab>,
    currentTab: MainTab?,
    onSelected: (MainTab) -> Unit,
    onReselected: (MainTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
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

@Composable
private fun MainBottomBarItem(
    modifier: Modifier = Modifier,
    item: MainTab,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Timber.d("MainBottomBar ${selected}, ${item}")
    Box(
        modifier = modifier
            .fillMaxHeight()
            .selectable(
                selected = selected,
                indication = null,
                role = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            ),
        contentAlignment = Alignment.Center,
    ) {
        val iconId = if (selected) item.selectedIconId else item.notSelectedIconId
        Image(
            painter = painterResource(iconId),
            contentDescription = item.contentDescription,
            modifier = Modifier.size(36.dp)
        )
    }
}