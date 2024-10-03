package com.chatteer.core.data.tcp.remote.models

/**
 * Description : Coroutines Network Base Response
 *
 * Created by juhongmin on 2024. 9. 16.
 */
sealed class ApiResponse<out T> {

    /**
     * Network Success Response Data
     */
    data class Success<out T>(val data: T) : ApiResponse<T>()

    /**
     * Network Fail
     * @param err Error
     */
    data class Fail(val err: JSendException) : ApiResponse<Nothing>()
}
