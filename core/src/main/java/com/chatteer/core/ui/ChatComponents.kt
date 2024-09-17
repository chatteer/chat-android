package com.chatteer.core.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
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
}
