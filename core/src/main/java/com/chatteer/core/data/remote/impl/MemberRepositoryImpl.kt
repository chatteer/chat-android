package com.chatteer.core.data.remote.impl

import com.chatteer.core.data.remote.apis.MemberApiService
import com.chatteer.core.data.remote.getOrNull
import com.chatteer.core.data.remote.repository.MemberRepository
import com.chatteer.core.model.MemberModel
import javax.inject.Inject

/**
 * Description :
 *
 * Created by juhongmin on 2024. 10. 3.
 */
internal class MemberRepositoryImpl @Inject constructor(
    private val memberApi: MemberApiService
) : MemberRepository {

    override suspend fun fetch(): MemberModel? {
        // 자연 스러운 매핑 고민해봐야할듯..
        return memberApi.fetch().getOrNull()
            ?.let { res ->
                MemberModel(
                    id = res.obj.id,
                    name = res.obj.name,
                    profileImageUrl = res.obj.profileImageUrl
                )
            }
    }
}