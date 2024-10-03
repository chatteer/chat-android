package com.chatteer.core.data.tcp.remote.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody

/**
 * Description : JSend Response Exception
 *
 * Created by juhongmin on 2024. 9. 16.
 */
sealed class JSendException(
    msg: String?,
    err: Throwable?
) : RuntimeException(msg, err) {
    data class Network(
        val msg: String? = null,
        val err: Throwable
    ) : JSendException(msg, err)

    /**
     * JSend 규칙에 안맞는 경우 에러 뱉도록 처리
     */
    data class Invalidate(
        val msg: String? = null,
    ) : JSendException(msg, null)

    data class EtcException(
        val err: Throwable
    ) : JSendException(null, err)

    data class JSendResponse(
        val statusCode: Int,
        val errBody: ResponseBody? = null,
        val err: Throwable? = null
    ) : JSendException(null, null) {

        val json: Json by lazy {
            Json {
                isLenient = true // Json 큰따옴표 느슨하게 체크.
                ignoreUnknownKeys = true // Field 값이 없는 경우 무시
                coerceInputValues = true // "null" 이 들어간경우 default Argument 값으로 대체
                encodeDefaults = true // 직렬화시 기본값 포함해서 셋팅
            }
        }

        @OptIn(ExperimentalSerializationApi::class)
        inline fun <reified T> getErrorBody(): T? {
            val body = errBody ?: return null
            return json.decodeFromString(body.string())
        }
    }

    inline fun <reified T> getBody(): T? {
        return if (this is JSendResponse) {
            try {
                this.getErrorBody()
            } catch (ex: Exception) {
                null
            }
        } else {
            null
        }
    }
}