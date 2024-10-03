package com.chatteer.core.data.remote.impl

import com.chatteer.core.data.remote.apis.FriendApiService
import com.chatteer.core.data.remote.getOrDefault
import com.chatteer.core.data.remote.repository.FriendRepository
import com.chatteer.core.data.tcp.remote.models.JSendList
import com.chatteer.core.model.FriendModel
import javax.inject.Inject

/**
 * Description :
 *
 * Created by juhongmin on 2024. 10. 3.
 */
internal class FriendRepositoryImpl @Inject constructor(
    private val apis: FriendApiService
) : FriendRepository {
    override suspend fun fetch(): List<FriendModel> {
        return apis.fetch()
            .getOrDefault(JSendList())
            .list.map {
                FriendModel(
                    id = it.id,
                    name = it.name,
                    profileImageUrl = it.profileImageUrl
                )
            }
    }
}