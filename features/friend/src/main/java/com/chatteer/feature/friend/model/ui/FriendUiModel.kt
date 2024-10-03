package com.chatteer.feature.friend.model.ui

import androidx.compose.runtime.Composable
import com.chatteer.core.ui.BaseClickEvent
import com.chatteer.core.ui.BaseUiModel

/**
 * Description :
 *
 * Created by juhongmin on 2024. 10. 3.
 */
data class FriendUiModel(
    val id: Long,
    val name: String,
    val imageUrl : String
) : BaseUiModel {
    override fun getType(): String {
        return "FriendUiModel"
    }

    @Composable
    override fun GetUi(clickEvent: (BaseClickEvent) -> Unit) {

    }
}