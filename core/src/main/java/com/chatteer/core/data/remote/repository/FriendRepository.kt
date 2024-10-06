package com.chatteer.core.data.remote.repository

import com.chatteer.core.model.FriendData

/**
 * Description :
 *
 * Created by juhongmin on 2024. 10. 3.
 */
interface FriendRepository {
    suspend fun fetch(): List<FriendData>
}
