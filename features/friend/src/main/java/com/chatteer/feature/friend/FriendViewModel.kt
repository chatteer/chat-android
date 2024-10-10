package com.chatteer.feature.friend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chatteer.feature.friend.model.ui.FriendMainModel
import com.chatteer.feature.friend.usecase.FriendUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Description : 친구 화면 ViewModel
 *
 * Created by juhongmin on 2024. 10. 1.
 */
@HiltViewModel
class FriendViewModel @Inject constructor(
    useCase: FriendUseCase
) : ViewModel() {

    val uiState: StateFlow<FriendMainModel> = useCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FriendMainModel.Loading
        )
}
