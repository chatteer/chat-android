package com.chatteer.core.data.remote.mapper

import com.chatteer.core.data.remote.models.entity.FriendEntity
import com.chatteer.core.model.FriendData

internal object FriendMapper {

    fun FriendEntity.toMap() = FriendData(
        id = this.id,
        name = this.name,
        profileImageUrl = this.profileImageUrl
    )
}
