package com.chatteer.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.chatteer.core.R

/**
 * Description : 채티어 테마 예시
 *
 * Created by juhongmin on 2024. 9. 17.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun HeaderType1() {
    val toolbarHeight = 200.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }
    var toolbarOffsetHeightPx by remember { mutableStateOf(0f) }
    val toolbarOffsetHeightDp = with(LocalDensity.current) { toolbarOffsetHeightPx.toDp() }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx + delta
                toolbarOffsetHeightPx = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "헤더 타입 1",
                        color = ChatTheme.color.white,
                        style = ChatTheme.text.h3M
                    )
                },
                navigationIcon = {
                    Box {
                        Image(
                            painter = painterResource(R.drawable.ic_arrow_left),
                            contentDescription = "Back",
                            modifier = Modifier.padding(15.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = ChatTheme.color.primary,
                    scrolledContainerColor = ChatTheme.color.primary
                ),
                modifier = Modifier
                    .height(max(toolbarHeight + toolbarOffsetHeightDp, 56.dp))
                    .offset(y = toolbarOffsetHeightDp)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(50) { index ->
                Text(
                    text = "Item $index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}