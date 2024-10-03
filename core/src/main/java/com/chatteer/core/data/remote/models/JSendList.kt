package com.chatteer.core.data.tcp.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Description :
 * {
 *  "status" : true or false,
 *  "message" : String (에러인경우 사용자에게 표시하는 에러 메시지),
 *  "data" : {
 *      "payload" : []
 *  }
 * }
 *
 * Created by juhongmin on 2024. 9. 16.
 */
@Serializable
data class JSendList<T : Any>(
    @SerialName("data")
    private val depthData: Payload<T>? = null
) : BaseJSend() {
    @Serializable
    data class Payload<T : Any>(
        @SerialName("payload")
        val list: List<T> = listOf()
    )

    override val isValid: Boolean get() = depthData != null

    val list: List<T>
        get() = depthData?.list ?: listOf()
}
