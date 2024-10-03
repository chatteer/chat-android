package com.chatteer.core.data.remote.models.entity

import kotlinx.serialization.Serializable

@Serializable
data class MemberEntity(
    val id : Long = 0,
    val name: String = "",
    val profileImageUrl: String = ""
)
