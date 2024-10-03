package com.chatteer.core.data.remote.apis

import com.chatteer.core.data.remote.models.entity.MemberEntity
import com.chatteer.core.data.tcp.remote.models.ApiResponse
import com.chatteer.core.data.tcp.remote.models.JSendObj
import retrofit2.http.GET

/**
 * Description :
 *
 * Created by juhongmin on 2024. 10. 3.
 */
interface MemberApiService {

    @GET("/api/v1/member")
    suspend fun fetch(): ApiResponse<JSendObj<MemberEntity>>
}