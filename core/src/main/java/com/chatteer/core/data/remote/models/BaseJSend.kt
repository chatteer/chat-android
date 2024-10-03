package com.chatteer.core.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Description :
 *
 * Created by juhongmin on 2024. 9. 16.
 */
@Serializable
open class BaseJSend {
    @SerialName("status")
    val isSuccess: Boolean = false

    @SerialName("message")
    val message: String? = null

    open val isValid : Boolean = false
}