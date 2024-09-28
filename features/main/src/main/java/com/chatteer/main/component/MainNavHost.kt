package com.chatteer.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chatteer.core.navigation.MainTabRoute
import com.chatteer.core.ui.ChatComponents
import com.chatteer.core.ui.ChatTheme
import com.chatteer.main.MainNavigator
import com.chatteer.main.MainTab
import com.chatteer.main.MainViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs

@Composable
internal fun MainNavHost(
    navigator: MainNavigator,
    paddingValues: PaddingValues,
    viewModel: MainViewModel
) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(ChatTheme.color.white)
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = MainTab.Friend.route
        ) {
            composable<MainTabRoute.Friend> {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("친구")
                    // CustomNumberPicker()
                    ChatComponents.CustomDateRollingPicker(
                        fromDate = LocalDate.now()
                            .withYear(2024)
                            .withMonth(6)
                            .withDayOfMonth(10),
                        initDate = LocalDate.now()
                            .withYear(2024)
                            .withMonth(9)
                            .withDayOfMonth(15),
                        visibleCount = 5,
                        selectedTextStyle = ChatTheme.text.h3M.copy(
                            color = ChatTheme.color.primary
                        ),
                        textStyle = ChatTheme.text.h4.copy(
                            color = ChatTheme.color.gray3
                        ),
                        selectBackground = { height ->
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxWidth()
                                    .height(height)
                                    .background(ChatTheme.color.gray4, RoundedCornerShape(8.dp))
                                    .alpha(0.5f)
                            )
                        }
                    ) { }
                }
            }
            composable<MainTabRoute.Chat> {
                Box {
                    Text("채팅")
                }
            }
            composable<MainTabRoute.More> {
                Box {
                    Text("더보기")
                }
            }
        }
    }
}

@Composable
private fun CustomNumberPicker() {
    val dateFormat = DateTimeFormatter.ofPattern("M월 d일 E", Locale.getDefault())
    val startDate = LocalDate.now()
        .withYear(2024)
        .withMonth(6)
        .withDayOfMonth(10)
    val endDate = LocalDate.now()
    var currentDate = startDate
    val dateList = mutableListOf<String>()
    dateList.addAll(listOf("", ""))
    while (!currentDate.isAfter(endDate)) {
        dateList.add(currentDate.format(dateFormat))
        currentDate = currentDate.plusDays(1)
    }
    dateList.addAll(listOf("", ""))

    // ui
    val itemPadding = PaddingValues(vertical = 8.dp)
    val selectedTextStyle = ChatTheme.text.h2B
    val defTextStyle = ChatTheme.text.h3
    val visibleItemsCount = 5
    val density = LocalDensity.current
    val itemHeight = with(density) {
        selectedTextStyle.fontSize.toDp()
            .plus(itemPadding.calculateTopPadding())
            .plus(itemPadding.calculateBottomPadding())
    }
    val itemHeightPx = with(density) { itemHeight.toPx() }
    val visibleItemsMiddle = remember { visibleItemsCount / 2 }

    val scrollState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = scrollState)

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemIndex }
            .map { index -> dateList[index + visibleItemsMiddle] }
            .distinctUntilChanged()
            .collect { item ->
                Timber.d("Selected Item $item")
            }
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(itemHeight)
                .background(ChatTheme.color.gray4, RoundedCornerShape(8.dp))
                .alpha(0.5f)
        )
        LazyColumn(
            state = scrollState,
            flingBehavior = flingBehavior,
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize()
                .height(itemHeight * visibleItemsCount)
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.5f to Color.Black,
                            1f to Color.Transparent
                        ), blendMode = BlendMode.DstIn
                    )
                }
        ) {
            itemsIndexed(
                items = dateList,
                key = { idx, _ -> idx },
                itemContent = { idx, item ->
                    val fraction by remember {
                        derivedStateOf {
                            val currentItem = scrollState
                                .layoutInfo
                                .visibleItemsInfo
                                .firstOrNull { it.key == idx } ?: return@derivedStateOf 0f
                            val fraction = (currentItem.offset - itemHeightPx
                                .times(visibleItemsMiddle))
                                .div(itemHeightPx)
                            abs(fraction.coerceIn(-1f, 1f))
                        }
                    }
                    Text(
                        text = item,
                        maxLines = 1,
                        modifier = Modifier
                            .height(itemHeight)
                            .wrapContentHeight(align = Alignment.CenterVertically)
                            .fillParentMaxWidth(),
                        style = defTextStyle.copy(
                            fontSize = lerp(
                                selectedTextStyle.fontSize,
                                defTextStyle.fontSize,
                                fraction
                            ),
                            color = lerp(
                                ChatTheme.color.primary,
                                ChatTheme.color.gray1,
                                fraction
                            )
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    }
}