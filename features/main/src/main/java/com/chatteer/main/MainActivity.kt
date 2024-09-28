package com.chatteer.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chatteer.core.ui.ChatTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Description :
 *
 * Created by juhongmin on 2024. 9. 16.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navigator = rememberMainNavigator()
            ChatTheme {
                MainScreen(
                    navigator = navigator,
                    viewModel = viewModel
                )
            }
        }
    }
}
