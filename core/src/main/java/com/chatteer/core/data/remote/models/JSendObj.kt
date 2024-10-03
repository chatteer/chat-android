package com.chatteer.core.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Description :
 * {
 *  "status" : true or false,
 *  "message" : String (에러인경우 사용자에게 표시하는 에러 메시지),
 *  "data" : {
 *      "payload" : { }
 *  }
 * }
 *
 * Created by juhongmin on 2024. 9. 16.
 */
@Serializable
data class JSendObj<T : Any>(
    @SerialName("data")
    private val depthData: Payload<T>? = null
) : BaseJSend() {
    @Serializable
    data class Payload<T : Any>(
        @SerialName("payload")
        val obj: T? = null
    )

    override val isValid: Boolean get() = depthData?.obj != null

    val obj: T
        get() = depthData?.obj ?: throw NullPointerException("Data is Null")
}
