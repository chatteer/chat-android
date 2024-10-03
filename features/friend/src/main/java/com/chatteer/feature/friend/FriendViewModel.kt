package com.chatteer.feature.friend

import androidx.lifecycle.ViewModel
import com.chatteer.feature.friend.usecase.FriendUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Description : 친구 화면 ViewModel
 *
 * Created by juhongmin on 2024. 10. 1.
 */
@HiltViewModel
class FriendViewModel @Inject constructor(
    private val useCase : FriendUseCase
) : ViewModel() {

}
