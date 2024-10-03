package com.chatteer.feature.friend.remote

import com.chatteer.core.data.tcp.remote.models.ApiResponse
import com.chatteer.core.data.tcp.remote.models.JSendList
import retrofit2.http.GET

/**
 * Description :
 *
 * Created by juhongmin on 2024. 10. 1.
 */
interface ApiService {
    @GET
    suspend fun fetch(): ApiResponse<JSendList<FriendEntity>>
}