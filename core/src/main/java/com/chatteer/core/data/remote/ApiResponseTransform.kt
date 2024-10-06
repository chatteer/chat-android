package com.chatteer.core.data.remote

import com.chatteer.core.data.remote.models.ApiResponse
import com.chatteer.core.data.remote.models.JSendException

/**
 * References Kotlin Result onSuccess
 */
inline fun <T> ApiResponse<T>.onSuccess(
    crossinline callback: (T) -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Success) {
        try {
            callback(this.data)
        } catch (ex: Exception) {
            return ApiResponse.Fail(JSendException.EtcException(ex))
        }
    }
    return this
}

/**
 * References Kotlin Result onFailure
 */
inline fun <T> ApiResponse<T>.onError(
    crossinline callback: (ApiResponse.Fail) -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Fail) {
        try {
            callback(this)
        } catch (ex: Exception) {
            // ignore
        }
    }
    return this
}

/**
 * ApiResponse Get Nullable
 */
inline fun <reified T> ApiResponse<T>.getOrNull(): T? {
    return if (this is ApiResponse.Success) {
        data
    } else {
        null
    }
}

/**
 * ApiResponse Get NonNull
 */
inline fun <reified T> ApiResponse<T>.getOrDefault(defValue: T): T {
    return if (this is ApiResponse.Success) {
        data
    } else {
        defValue
    }
}

@JvmName("map_with_result")
inline fun <reified I, reified O> ApiResponse<I>.map(
    crossinline predicate: (I) -> O
): Result<O> {
    return when (this) {
        is ApiResponse.Success -> {
            try {
                Result.success(predicate(data))
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        }

        is ApiResponse.Fail -> {
            Result.failure(err)
        }
    }
}