package com.chatteer.feature.friend

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.chatteer.core.ui.ChatComponents
import com.chatteer.core.ui.ChatTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Description :
 *
 * Created by juhongmin on 2024. 10. 1.
 */
@Composable
fun FriendScreen(
    navBackStackEntry: NavBackStackEntry,
    viewModel: FriendViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    ChatComponents.HeaderAndContents(
        navigationHeader = {
            Text(
                text = "친구",
                style = ChatTheme.text.h3,
                modifier = Modifier
                    .align(Alignment.CenterStart)
            )
        }
    ) {

    }
}
