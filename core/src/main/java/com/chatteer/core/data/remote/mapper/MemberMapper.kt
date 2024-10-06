package com.chatteer.core.data.remote.mapper

import com.chatteer.core.data.remote.models.entity.MemberEntity
import com.chatteer.core.model.MemberData

internal object MemberMapper {

    fun MemberEntity.toMap() = MemberData(
        id = this.id,
        name = this.name,
        profileImageUrl = this.profileImageUrl
    )
}