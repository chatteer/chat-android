package com.chatteer.feature.friend

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Description :
 *
 * Created by juhongmin on 2024. 10. 3.
 */

class ViewModelPreviewParameterProvider : PreviewParameterProvider<ViewModel> {
    override val values: Sequence<ViewModel>
        get() = sequenceOf(

        )
}