package com.chatteer.core.data.tcp.remote

/**
 * Description : 외부에서 네트워크 사용할 Provider Class
 *
 * Created by juhongmin on 2024. 9. 16.
 */
interface NetworkProvider {
    fun <T> createApi(service: Class<T>): T
}