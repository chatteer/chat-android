package com.chatteer.feature.friend

import com.chatteer.core.data.remote.repository.FriendRepository
import com.chatteer.core.data.remote.repository.MemberRepository
import com.chatteer.feature.friend.usecase.FriendUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import timber.log.Timber
import javax.inject.Inject

/**
 * Description :
 *
 * Created by juhongmin on 2024. 10. 6.
 */
@HiltAndroidTest
class FriendRepositoryTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var friendRepository: FriendRepository

    @Inject
    lateinit var memberRepository: MemberRepository

    @Inject
    lateinit var useCase: FriendUseCase

    @Before
    fun init(){
        hiltRule.inject()
        Timber.plant(object : Timber.DebugTree() {

            override fun createStackElementTag(element: StackTraceElement): String {
                val str = StringBuilder("ChatTimber")
                try {
                    val className = element.className.substringAfterLast(".")
                        .substringBefore("$")
                    val methodName = element.methodName.substringAfterLast(".")
                    str.append(className)
                        .append(":")
                        .append(methodName)
                } catch (ex: Exception) {
                    // ignore
                }
                return str.toString()
            }
        })
    }

    @Test
    fun test_fake_repository(){
        runBlocking {
            val res = friendRepository.fetch()
            println(res)
            val member = memberRepository.fetch()
            println(member)
        }
    }

    @Test
    fun test_fake_usecase_init(){
        runBlocking {
            val res = useCase().first()
            println(res)
        }
    }
}