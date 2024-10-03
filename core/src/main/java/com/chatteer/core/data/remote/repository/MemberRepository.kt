package com.chatteer.core.data.remote.repository

import com.chatteer.core.model.MemberModel

/**
 * Description :
 *
 * Created by juhongmin on 2024. 10. 3.
 */
interface MemberRepository {
    suspend fun fetch(): MemberModel?
}