package com.chatteer.core.data.remote.impl

import com.chatteer.core.data.remote.apis.MemberApiService
import com.chatteer.core.data.remote.fake.FakeRepository
import com.chatteer.core.data.remote.map
import com.chatteer.core.data.remote.mapper.MemberMapper.toMap
import com.chatteer.core.data.remote.repository.MemberRepository
import com.chatteer.core.model.MemberData
import javax.inject.Inject

/**
 * Description :
 *
 * Created by juhongmin on 2024. 10. 3.
 */
internal class MemberRepositoryImpl @Inject constructor(
    private val fakeRepository: FakeRepository,
    private val memberApi: MemberApiService
) : MemberRepository {

    override suspend fun fetch(): Result<MemberData> {
        return fakeRepository.fetchMember()
            .map { it.obj.toMap() }
        // 자연 스러운 매핑 고민해봐야할듯..
//        return memberApi.fetch()
//            .map { it.obj.toMap() }
    }
}