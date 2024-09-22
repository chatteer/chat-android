package com.chatteer.core.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chatteer.core.R
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
        callback: (String) -> Unit
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
                                callback(text.value)
                            }
                        },
                        onDone = {
                            if (!isError.value) {
                                isFocused = false
                                focusManager.clearFocus()
                                callback(text.value)
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
    fun HeaderAndContents(
        navigationHeader: @Composable BoxScope.() -> Unit,
        collapseHeader: (@Composable BoxScope.() -> Unit)? = null,
        stickyHeader: (@Composable BoxScope.() -> Unit)? = null,
        contents: @Composable BoxScope.() -> Unit
    ) {
        val density = LocalDensity.current
        val scrollState = rememberScrollState()
        val navigationHeight = 56.dp
        val navigationHeightPx = with(density) { 56.dp.toPx() }
        var collapseHeightPx by remember { mutableStateOf(0F) }
        var stickyHeightPx by remember { mutableStateOf(0F) }
        var headerOffsetPx by remember { mutableStateOf(0F) }
        var collapseVisiblePercentage by remember { mutableStateOf(0F) }

        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val dy = available.y
                    // 접히는 레이아웃 숨겨진 상태
                    return if (dy < 0) {
                        // 터치가 아래에서 위로 (아래로 스크롤)
                        if (collapseHeader != null && collapseVisiblePercentage <= 0f) {
                            println("Timber onPreScroll 접히는 레이아웃이 접혔을때 $headerOffsetPx")
                            Offset.Zero
                        } else {
                            println("Timber 아래로 스크롤합니다. 보편적으로 ${headerOffsetPx}, $dy")
                            scrollState.dispatchRawDelta(dy * -1)
                            val offset = headerOffsetPx.plus(dy)
                            headerOffsetPx = offset.coerceIn(-collapseHeightPx, 0F)
                            Offset(0f, dy)
                        }
                    } else {
                        // 터치가 위에서 아래로 (위로 스크롤)
                        // 접히는 뷰가 접혔을때
                        if (collapseHeader != null && collapseVisiblePercentage <= 0F) {
                            // scrollState.dispatchRawDelta(dy)
                            // val offset = headerOffsetPx.plus(dy)
                            // headerOffsetPx = offset.coerceIn(-collapseHeightPx, 0F)
                            val consumed =  scrollState.dispatchRawDelta(-dy)
                            println("Timber 위로 스크롤, 접혔을때 ${consumed}")
                            Offset.Zero
                        } else {
                            println("Timber 여기탑니다. $headerOffsetPx")
                            val offset = headerOffsetPx.plus(dy)
                            headerOffsetPx = offset.coerceIn(-collapseHeightPx, 0F)
                            Offset.Zero
                        }
                    }
//                    return if (collapseHeader != null && collapseVisiblePercentage <= 0f) {
//                        println("Timber onPreScroll 접히는 레이아웃이 접혔을때 $headerOffsetPx")
//                        Offset.Zero
//                    } else if (dy < 0) {
//                        println("Timber onPreScroll else if dy<0 $headerOffsetPx")
//                        // 터치가 아래에서 위로
//                        scrollState.dispatchRawDelta(dy * -1)
//                        val offset = headerOffsetPx.plus(dy)
//                        // -headerMaxHeightPx ~ 0F
//                        headerOffsetPx = offset.coerceIn(-collapseHeightPx, 0F)
//                        Offset(0f,dy)
//                    } else {
//                        val offset = headerOffsetPx.plus(dy)
//                        // -headerMaxHeightPx ~ 0F
//                        headerOffsetPx = offset.coerceIn(-collapseHeightPx, 0F)
//                        println("Timber onPreScroll else $headerOffsetPx")
//                        Offset.Zero
//                    }
//                    val offset = headerOffsetPx.plus(dy)
//                    // -headerMaxHeightPx ~ 0F
//                    headerOffsetPx = offset.coerceIn(-collapseHeightPx, 0F)
//                    println("Timber $headerOffsetPx")
//                    return Offset.Zero
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(navigationHeight)
                    .background(ChatTheme.color.primary)
            ) { navigationHeader() }
            if (collapseHeader != null) {
//                Box(
//                    modifier = Modifier
//                        .padding(top = navigationHeight)
//                        .fillMaxWidth()
//                        .onSizeChanged { collapseHeightPx = it.height.toFloat() }
//                        .verticalScroll(scrollState)
//                        .onGloballyPositioned { coordinates ->
//                            val height = coordinates.boundsInRoot().height
//                            collapseVisiblePercentage = height / collapseHeightPx
//                        }
//                        .offset(y = with(density) {
//                            headerOffsetPx.toDp()
//                        })
//                        .alpha(collapseVisiblePercentage)
//                ) { collapseHeader() }
                Column(
                    modifier = Modifier
                        .padding(top = navigationHeight)
                        .fillMaxWidth()
                        .onSizeChanged { collapseHeightPx = it.height.toFloat() }
                        .verticalScroll(scrollState)
                        .offset(y = with(density) {
                            headerOffsetPx.toDp()
                        })
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .onGloballyPositioned { coordinates ->
                                val height = coordinates.boundsInRoot().height
                                collapseVisiblePercentage = height / collapseHeightPx
                            }
                            .alpha(collapseVisiblePercentage)
                    ) { collapseHeader() }
                }
            }
            if (stickyHeader != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = with(density) {
                            headerOffsetPx
                                .plus(navigationHeightPx)
                                .plus(collapseHeightPx)
                                .toDp()
                        })
                        .onSizeChanged { stickyHeightPx = it.height.toFloat() }
                ) { stickyHeader() }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = with(density) {
                        abs(
                            headerOffsetPx
                                .plus(navigationHeightPx)
                                .plus(collapseHeightPx)
                                .plus(stickyHeightPx)
                        ).toDp()
                    })
            ) { contents() }
        }

//        LaunchedEffect(collapseHeight) {
//            collapseHeightPx = with(density) {
//                this.density.toPx
//            }
//        }
    }
}
