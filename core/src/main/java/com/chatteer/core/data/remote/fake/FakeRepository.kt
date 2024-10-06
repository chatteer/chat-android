package com.chatteer.core.data.remote.fake

import android.content.Context
import com.chatteer.core.R
import com.chatteer.core.data.DataExtensions
import com.chatteer.core.data.remote.models.ApiResponse
import com.chatteer.core.data.remote.models.JSendList
import com.chatteer.core.data.remote.models.JSendList.Payload
import com.chatteer.core.data.remote.models.JSendObj
import com.chatteer.core.data.remote.models.entity.FriendEntity
import com.chatteer.core.data.remote.models.entity.MemberEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Description :
 *
 * Created by juhongmin on 2024. 10. 6.
 */
internal class FakeRepository(
    context: Context,
    private val json: Json,
    private val httpClient: OkHttpClient
) {

    private val baseUrl: String by lazy {
        context.getString(R.string.BaseUrl)
    }

    @Serializable
    internal data class FakeGoodsEntity(
        val id: Long = 0,
        @SerialName("user_id")
        val userId: String = "",
        val tag: Int,
        val title: String = "",
        val contents: String = "",
        @SerialName("register_date")
        val registerDate: LocalDateTime = DataExtensions.nowLocalDateTime()
    )

    @Serializable
    internal data class FakeFileEntity(
        val id: Long = 0,
        @SerialName("original_name")
        val name: String = "",
        @SerialName("path")
        val imageUrl: String = "",
        @SerialName("mime_type")
        val mimeType: String = "",
        @SerialName("register_date")
        val registerDate: LocalDateTime = DataExtensions.nowLocalDateTime()
    )

    @Serializable
    internal data class FakeMemoEntity(
        val id: Long = 0,
        val title: String = "",
        val contents: String = "",
        @SerialName("register_date")
        val registerDate: LocalDateTime = DataExtensions.nowLocalDateTime()
    )

    private suspend fun fetchGoods(): List<FakeGoodsEntity> {
        return withContext(Dispatchers.IO) {
            val call = httpClient.newCall(
                Request.Builder()
                    .url(baseUrl.plus("/api/v1/memo?pageNo=1&pageSize=100"))
                    .build()
            )
            val body = call.execute().body!!
            json.decodeFromString<JSendList<FakeGoodsEntity>>(body.string()).list
        }
    }

    private suspend fun fetchFile(): List<FakeFileEntity> {
        return withContext(Dispatchers.IO) {
            val call = httpClient.newCall(
                Request.Builder()
                    .url(baseUrl.plus("/api/v1/uploads?pageNo=1&pageSize=200"))
                    .build()
            )
            val body = call.execute().body!!
            json.decodeFromString<JSendList<FakeFileEntity>>(body.string()).list
        }
    }

    private suspend fun fetchMemo(): List<FakeMemoEntity> {
        return withContext(Dispatchers.IO) {
            val call = httpClient.newCall(
                Request.Builder()
                    .url(baseUrl.plus("/api/v1/memo/aos"))
                    .build()
            )
            val body = call.execute().body!!
            json.decodeFromString<JSendList<FakeMemoEntity>>(body.string()).list
        }
    }

    suspend fun fetchFriend(): ApiResponse<JSendList<FriendEntity>> {
        return withContext(Dispatchers.IO) {
            val list = combine(
                flowOf(fetchGoods()),
                flowOf(fetchFile().filter { it.mimeType.startsWith("image") })
            ) { goodsList, fileList ->
                val list = mutableListOf<FriendEntity>()
                goodsList.forEachIndexed { index, entity ->
                    val imageUrl = fileList[index.coerceAtMost(fileList.lastIndex)].imageUrl
                    list.add(
                        FriendEntity(
                            id = entity.id,
                            name = entity.userId,
                            profileImageUrl = imageUrl
                        )
                    )
                }
                return@combine list
            }.first()
            ApiResponse.Success(JSendList(depthData = Payload(list = list)))
        }
    }

    suspend fun fetchMember(): ApiResponse<JSendObj<MemberEntity>> {
        return withContext(Dispatchers.IO) {
            val obj = combine(
                flowOf(fetchMemo()),
                flowOf(fetchFile().filter { it.mimeType.startsWith("image") })
            ) { memoList, fileList ->
                val memo = memoList[0]
                return@combine MemberEntity(
                    id = fileList.last().id,
                    name = memo.title,
                    profileImageUrl = fileList.last().imageUrl
                )
            }.first()
            ApiResponse.Success(JSendObj(depthData = JSendObj.Payload(obj = obj)))
        }
    }
}