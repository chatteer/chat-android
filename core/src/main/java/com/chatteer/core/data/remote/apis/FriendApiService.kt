package com.chatteer.core.data.remote.apis

import com.chatteer.core.data.remote.models.entity.FriendEntity
import com.chatteer.core.data.remote.models.ApiResponse
import com.chatteer.core.data.remote.models.JSendList
import retrofit2.http.GET

/**
 * Description :
 *
 * Created by juhongmin on 2024. 10. 3.
 */
internal interface FriendApiService {
    @GET("/api/v1/friend")
    suspend fun fetch(): ApiResponse<JSendList<FriendEntity>>
}