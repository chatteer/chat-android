package com.chatteer.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chatteer.core.ui.StatusBar
import com.chatteer.core.ui.StatusBarChangeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Description : MainViewModel
 *
 * Created by juhongmin on 2024. 9. 16.
 */
@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {

    val statusBar: StateFlow<StatusBar> = StatusBarChangeEvent.events
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = StatusBar()
        )
}
