package com.chatteer.core.data.remote.models.entity

import kotlinx.serialization.Serializable

@Serializable
internal data class FriendEntity(
    val id: Long = 0,
    val name: String = "",
    val profileImageUrl: String = ""
)
