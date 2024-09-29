package com.chatteer.core.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chatteer.core.R
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs

/**
 * Description : Chat UI Component
 *
 * Created by juhongmin on 2024. 9. 17.
 */
object ChatComponents {

    @Composable
    fun TextFieldTextType(
        modifier: Modifier,
        initValue: String = "",
        initFocus: Boolean = false,
        isEnable: Boolean = true,
        textStyle: TextStyle = ChatTheme.text.h4M,
        placeHolder: @Composable () -> Unit = {},
        imeAction: ImeAction = ImeAction.Next,
        callback: ((String) -> Unit)? = null,
        confirm: (String) -> Unit
    ) {
        val text = remember { mutableStateOf(initValue) }
        var isFocused by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current
        val focusRequester = remember { FocusRequester() }
        val isError = remember { mutableStateOf(false) }
        Box(
            modifier = modifier
                .onFocusChanged { isFocused = it.isFocused }
                .border(
                    2.dp,
                    if (isError.value) {
                        ChatTheme.color.red
                    } else if (isFocused) {
                        ChatTheme.color.primary
                    } else {
                        ChatTheme.color.gray3
                    },
                    RoundedCornerShape(8.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 10.dp, horizontal = 10.dp)
            ) {
                BasicTextField(
                    value = text.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    enabled = isEnable,
                    onValueChange = { newText ->
                        text.value = newText
                    },
                    textStyle = textStyle,
                    keyboardOptions = KeyboardOptions(
                        imeAction = imeAction,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            if (!isError.value) {
                                isFocused = false
                                focusManager.moveFocus(FocusDirection.Next)
                                confirm(text.value)
                            }
                        },
                        onDone = {
                            if (!isError.value) {
                                isFocused = false
                                focusManager.clearFocus()
                                confirm(text.value)
                            }
                        }
                    ),
                    singleLine = true
                )

                if (text.value.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                    ) {
                        placeHolder()
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_clear),
                            contentDescription = null,
                            modifier = Modifier
                                .height(15.dp)
                                .clickable { text.value = "" }
                        )
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            if (initFocus) {
                focusRequester.requestFocus()
            }
        }
        LaunchedEffect(text.value) {
            callback?.invoke(text.value)
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @SuppressLint("ModifierParameter")
    @Composable
    fun ImageLoader(
        imageUrl: String,
        contentScale: ContentScale = ContentScale.Crop,
        modifier: Modifier = Modifier,
    ) {
        GlideImage(
            model = imageUrl,
            contentDescription = null,
            modifier = modifier,
            loading = placeholder(ColorPainter(ChatTheme.color.gray4)),
            failure = placeholder(R.drawable.ic_error),
            contentScale = contentScale
        ) { requestBuilder ->
            requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE)
        }
    }

    @Composable
    fun VerticalSpace(height: Int) {
        Spacer(modifier = Modifier.height(height.dp))
    }

    @Composable
    fun HorizontalSpace(width: Int) {
        Spacer(modifier = Modifier.width(width.dp))
    }

    @Composable
    fun HeaderAndContents(
        navigationHeader: @Composable BoxScope.() -> Unit,
        collapseHeader: (@Composable BoxScope.() -> Unit)? = null,
        stickyHeader: (@Composable BoxScope.() -> Unit)? = null,
        scrollState: ScrollableState? = null,
        contents: @Composable BoxScope.() -> Unit
    ) {
        val density = LocalDensity.current
        val navigationHeight = 56.dp
        val navigationHeightPx = with(density) { 56.dp.toPx() }
        var collapseHeightPx by remember { mutableFloatStateOf(0F) }
        var stickyHeightPx by remember { mutableFloatStateOf(0F) }
        var headerOffsetPx by remember { mutableFloatStateOf(0F) }
        var isInnerScrollAtStart by remember { mutableStateOf(true) }

        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val dy = available.y
                    return when {
                        isInnerScrollAtStart && dy > 0 && headerOffsetPx < 0 -> {
                            val newOffset = headerOffsetPx + dy
                            headerOffsetPx = newOffset.coerceIn(-collapseHeightPx, 0f)
                            Offset(0f, dy)
                        }

                        dy < 0 && headerOffsetPx > -collapseHeightPx -> {
                            val newOffset = headerOffsetPx + dy
                            headerOffsetPx = newOffset.coerceIn(-collapseHeightPx, 0f)
                            Offset(0f, dy)
                        }

                        else -> {
                            Offset.Zero
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {
            // Collapse Header
            if (collapseHeader != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = with(density) { (navigationHeightPx + headerOffsetPx).toDp() })
                        .onSizeChanged { collapseHeightPx = it.height.toFloat() }
                ) {
                    collapseHeader()
                }
            }

            // Sticky Header
            if (stickyHeader != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = with(density) {
                            (navigationHeightPx
                                .plus(collapseHeightPx)
                                .plus(headerOffsetPx))
                                .toDp()
                        })
                        .onSizeChanged { stickyHeightPx = it.height.toFloat() }
                ) { stickyHeader() }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = with(density) {
                        (navigationHeightPx
                            .plus(collapseHeightPx)
                            .plus(stickyHeightPx)
                            .plus(headerOffsetPx)).toDp()
                    })
            ) { contents() }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(navigationHeight)
                    .background(ChatTheme.color.primary)
            ) { navigationHeader() }
        }

        if (scrollState is LazyListState) {
            LaunchedEffect(scrollState) {
                snapshotFlow {
                    scrollState.firstVisibleItemIndex == 0 &&
                            scrollState.firstVisibleItemScrollOffset == 0
                }.collect { isInnerScrollAtStart = it }
            }
        } else if (scrollState is LazyGridState) {
            LaunchedEffect(scrollState) {
                snapshotFlow {
                    scrollState.firstVisibleItemIndex == 0 &&
                            scrollState.firstVisibleItemScrollOffset == 0
                }.collect { isInnerScrollAtStart = it }
            }
        } else if (scrollState is ScrollState) {
            LaunchedEffect(scrollState) {
                snapshotFlow { scrollState.value == 0 }
                    .collect { isInnerScrollAtStart = it }
            }
        }
    }

    @Composable
    fun CustomText(
        text: String,
        textStyle: TextStyle = ChatTheme.text.h3M,
        textColor: Color = ChatTheme.color.black,
        textAlignment: Alignment = Alignment.Center,
        paddingValues: PaddingValues = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
        modifier: Modifier = Modifier
            .fillMaxWidth()
            .background(ChatTheme.color.white, RoundedCornerShape(6.dp)),
        clickCallback: () -> Unit = {}
    ) {
        Box(
            modifier = modifier
                .clickable { clickCallback() }
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .padding(paddingValues)
                    .align(textAlignment),
                style = textStyle,
                color = textColor
            )
        }
    }

    @Composable
    fun CustomText(
        modifier: Modifier = Modifier,
        text: String,
        isEnable: Boolean = true,
        textAlignment: Alignment = Alignment.Center,
        paddingValues: PaddingValues = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
        enableTextStyle: TextStyle = ChatTheme.text.h3M,
        enableTextColor: Color = ChatTheme.color.white,
        enableModifier: Modifier = Modifier
            .fillMaxWidth()
            .background(ChatTheme.color.primary, RoundedCornerShape(6.dp)),
        disableModifier: Modifier = Modifier
            .fillMaxWidth()
            .background(ChatTheme.color.gray3, RoundedCornerShape(6.dp)),
        disableTextStyle: TextStyle = ChatTheme.text.h3M,
        disableTextColor: Color = ChatTheme.color.white,
        clickCallback: () -> Unit = {}
    ) {
        Box(
            modifier = modifier
                .then(
                    if (isEnable) enableModifier
                    else disableModifier
                )
                .then(Modifier.clickable { clickCallback() })
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .padding(paddingValues)
                    .align(textAlignment),
                style = if (isEnable) {
                    enableTextStyle
                } else {
                    disableTextStyle
                },
                color = if (isEnable) {
                    enableTextColor
                } else {
                    disableTextColor
                }
            )
        }
    }

    fun Modifier.drawTopBorder(color: Color, strokeWidth: Dp) = composed(
        factory = {
            val density = LocalDensity.current
            val strokeWidthPx = density.run { strokeWidth.toPx() }

            Modifier.drawWithContent {
                drawContent()
                drawLine(
                    color = color,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = strokeWidthPx
                )
            }
        }
    )



    @Composable
    fun CustomDateRollingPicker(
        fromDate: LocalDate, // start Date
        toDate: LocalDate = LocalDate.now(), // end Date
        dateFormat: DateTimeFormatter = DateTimeFormatter
            .ofPattern("M월 d일 E", Locale.getDefault()),
        initDate: LocalDate = LocalDate.now(),
        visibleCount: Int = 5,
        selectedTextStyle: TextStyle = LocalTextStyle.current,
        textStyle: TextStyle = LocalTextStyle.current,
        itemPadding: PaddingValues = PaddingValues(vertical = 8.dp),
        selectBackground: @Composable BoxScope.(height: Dp) -> Unit,
        state: DateRollingPickerState = rememberDateRollingPickerState()
    ) {
        val emptyList = List(visibleCount / 2) { "-" }
        val dayList = remember {
            mutableListOf<String>().apply {
                addAll(emptyList)
                var currDate = fromDate
                while (!currDate.isAfter(toDate)) {
                    add(currDate.format(dateFormat))
                    currDate = currDate.plusDays(1)
                }
                addAll(emptyList)
            }
        }
        val hourList = remember { (1..12)
            .map { String.format("%02d",it,Locale.getDefault()) }
        }
        Timber.d("HourList ${hourList}")

        val density = LocalDensity.current
        val itemHeight = with(density) {
            selectedTextStyle.fontSize.toDp()
                .plus(itemPadding.calculateTopPadding())
                .plus(itemPadding.calculateBottomPadding())
        }
        val itemHeightPx = with(density) { itemHeight.toPx() }
        val visibleMiddle = remember { visibleCount / 2 }
        val dayScrollState = rememberLazyListState()
        val dayFlingBehavior = rememberSnapFlingBehavior(lazyListState = dayScrollState)
        val hourScrollState = rememberLazyListState()
        val hourFlingBehavior = rememberSnapFlingBehavior(lazyListState = hourScrollState)
        val minuteScrollState = rememberLazyListState()
        val minuteFlingBehavior = rememberSnapFlingBehavior(lazyListState = minuteScrollState)

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            selectBackground(itemHeight)
            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(itemHeight * visibleCount)
                    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.verticalGradient(
                                0f to Color.Transparent,
                                0.25f to Color.Black,
                                0.5f to Color.Black,
                                0.75f to Color.Black,
                                1f to Color.Transparent
                            ), blendMode = BlendMode.DstIn
                        )
                    }
            ) {
                // Day Rolling
                LazyColumn(
                    state = dayScrollState,
                    flingBehavior = dayFlingBehavior,
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxHeight()
                ) {
                    itemsIndexed(
                        items = dayList,
                        key = { idx, _ -> idx },
                        itemContent = { idx, item ->
                            val fraction by remember {
                                derivedStateOf {
                                    val currentItem = dayScrollState
                                        .layoutInfo
                                        .visibleItemsInfo
                                        .firstOrNull { it.key == idx } ?: return@derivedStateOf 0f
                                    val fraction = (currentItem.offset - itemHeightPx
                                        .times(visibleMiddle))
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
                                style = textStyle.copy(
                                    fontSize = lerp(
                                        selectedTextStyle.fontSize,
                                        textStyle.fontSize,
                                        fraction
                                    ),
                                    color = lerp(
                                        selectedTextStyle.color,
                                        textStyle.color,
                                        fraction
                                    )
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    )
                }

                // Hour Rolling
                LazyColumn(
                    state = hourScrollState,
                    flingBehavior = hourFlingBehavior
                ) {  }
            }
        }

        LaunchedEffect(Unit) {
            snapshotFlow { dayScrollState.firstVisibleItemIndex }
                .map { index -> dayList[index + visibleMiddle] }
                .distinctUntilChanged()
                .onEach {
                    Timber.d("Current Item $it")
                }
                .launchIn(this)
        }
    }
}
