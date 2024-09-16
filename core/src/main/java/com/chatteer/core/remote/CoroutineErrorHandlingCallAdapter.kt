package com.chatteer.core.remote

import com.chatteer.core.remote.models.ApiResponse
import com.chatteer.core.remote.models.BaseJSend
import com.chatteer.core.remote.models.JSendException
import com.chatteer.core.remote.models.JSendList
import com.chatteer.core.remote.models.JSendListWithMeta
import com.chatteer.core.remote.models.JSendObj
import com.chatteer.core.remote.models.JSendObjWithMeta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Description : Coroutines Call Adapter
 *
 * Created by juhongmin on 2024. 9. 16.
 */
class CoroutineErrorHandlingCallAdapter(
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : CallAdapter.Factory() {

    companion object {
        fun create(): CallAdapter.Factory {
            return CoroutineErrorHandlingCallAdapter()
        }
    }

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        return when (getRawType(returnType)) {
            Call::class.java -> {
                // Continuation::class.java -> 기억해둘것
                // ApiResponse<JSend...> -> Original (Call<ApiResponse<JSend...>)
                val callType = getParameterUpperBound(0, returnType as ParameterizedType)

                // ApiResponse 으로 감싸져 있는지 확인
                if (getRawType(callType) == ApiResponse::class.java) {
                    // ApiResponse Find {<JSend...>}
                    val type = getParameterUpperBound(0, callType as ParameterizedType)
                    CoroutineCallAdapterWrapper(type, coroutineScope)
                } else {
                    null
                }
            }

            else -> null
        }
    }

    inner class CoroutineCallAdapterWrapper<R : Type>(
        private val responseType: R,
        private val coroutineScope: CoroutineScope,
    ) : CallAdapter<R, Any> {
        // CallAdapter<Request,Response>

        /**
         * @see JSendObj
         * @see JSendObjWithMeta
         * @see JSendList
         * @see JSendListWithMeta
         * 이외는 isValid 체크 X
         */
        override fun responseType(): Type {
            return responseType
        }

        override fun adapt(call: Call<R>): Any {
            return CallEnqueueDelegate(call, coroutineScope)
        }
    }

    inner class CallEnqueueDelegate<T>(
        private val originCall: Call<T>,
        private val coroutineScope: CoroutineScope,
    ) : Call<Any> {
        override fun clone(): Call<Any> {
            return CallEnqueueDelegate(originCall.clone(), coroutineScope)
        }

        override fun execute(): Response<Any> {
            return runBlocking(coroutineScope.coroutineContext) {
                val res: Any = try {
                    val apiResponse = getApiResponse(originCall.execute())
                    apiResponse
                } catch (err: Throwable) {
                    ApiResponse.Fail(getJSendException(err))
                }
                Response.success(res)
            }
        }

        override fun enqueue(callback: Callback<Any>) {
            coroutineScope.launch {
                val res: Response<Any> = try {
                    val apiResponse = getApiResponse(originCall.awaitResponse())
                    Response.success(apiResponse)
                } catch (err: Throwable) {
                    Response.success(ApiResponse.Fail(getJSendException(err)))
                }
                callback.onResponse(this@CallEnqueueDelegate, res)
            }
        }

        override fun isExecuted(): Boolean {
            return originCall.isExecuted
        }

        override fun cancel() {
            return originCall.cancel()
        }

        override fun isCanceled(): Boolean {
            return originCall.isCanceled
        }

        override fun request(): Request {
            return originCall.request()
        }

        override fun timeout(): Timeout {
            return originCall.timeout()
        }
    }

    @Throws(JSendException.Invalidate::class)
    private fun validateJSendCheck(res: Any): Any {
        return if (checkPayload(res)) {
            res
        } else if (res is BaseJSend) {
            throw JSendException.Invalidate(res.message)
        } else {
            throw JSendException.Invalidate("Invalid Exception")
        }
    }

    /**
     * data.payload 데이터 유효한지 체크하는 함수
     */
    @Throws(JSendException.Invalidate::class)
    private fun checkPayload(res: Any): Boolean {
        return when (res) {
            is BaseJSend -> {
                if (res.isValid) {
                    true
                } else if (res.isSuccess) {
                    throw JSendException.Invalidate(res.message)
                } else {
                    false
                }
            }

            else -> true
        }
    }

    private fun getJSendException(err: Throwable): JSendException {
        return if (err is HttpException) {
            val res = err.response()
            if (res != null) {
                JSendException.JSendResponse(err.code(), res.errorBody(), err)
            } else {
                JSendException.Network(err.message, err)
            }
        } else if (err is SocketTimeoutException) {
            JSendException.Network(err.message, err)
        } else if (err is UnknownHostException) {
            JSendException.Network(err.message, err)
        } else if (err is IOException) {
            JSendException.Network(err.message, err)
        } else {
            JSendException.Network(err.message, err)
        }
    }

    /**
     * Converter HTTP Response -> ApiResponse
     * @param res HTTP 통해 전달 받은 데이터
     */
    private fun getApiResponse(res: Response<*>): ApiResponse<Any> {
        return try {
            val responseBody = res.body()
            if (res.isSuccessful && responseBody != null) {
                val body = validateJSendCheck(responseBody)
                ApiResponse.Success(body)
            } else {
                ApiResponse.Fail(
                    JSendException.JSendResponse(res.code(), res.errorBody())
                )
            }
        } catch (ex: Throwable) {
            ApiResponse.Fail(getJSendException(ex))
        }
    }

    /**
     * HTTP Error 발생시 메시지 리턴하는 함수
     * @param res [okhttp3.Response]
     *
     * @return {HTTP Status Code}, {GET,POST,PUT}, {Request API Path}
     * @see [HTTP Code 100..599]
     * @see [Method GET, POST, PUT..]
     * @see [ RequestPath ]
     */
    private fun getHttpErrorMsg(res: okhttp3.Response?): CharSequence {
        val errCode = res?.code ?: -1
        val msg = StringBuilder()
        if (res != null) {
            msg.append(errCode)
            msg.append(" [${res.request.method}]")
            msg.append(":")
            msg.append(res.request.url.encodedPath)
        } else {
            msg.append("[UN_KNOWN]")
        }

        return msg.toString()
    }
}
