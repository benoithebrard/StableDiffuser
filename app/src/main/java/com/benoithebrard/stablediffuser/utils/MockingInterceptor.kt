package com.benoithebrard.stablediffuser.utils

import android.content.Context
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.nio.charset.Charset

private const val CHARSET_UTF_8 = "UTF-8"
private const val HTTP_CODE_OK = 200
private const val MOCKED_HTTP_DELAY_MS = 500L
private const val APPLICATION_JSON = "application/json"

class MockingInterceptor(
    private val appContext: Context,
    private val mockings: List<Mocking>
) : Interceptor {

    data class Mocking(
        val urlMatcher: (String) -> Boolean,
        val jsonFileName: String,
        val errorCode: Int? = null,
        val header: Pair<String, String>? = null
    )

    private val jsonType: MediaType? by lazy {
        APPLICATION_JSON.toMediaTypeOrNull()
    }

    override fun intercept(
        chain: Interceptor.Chain
    ): Response {
        val request = chain.request()
        val requestUrl = request.url.toUrl().toString()

        return getMockingForUrl(
            url = requestUrl
        )?.let { mocking ->
            readFile(mocking.jsonFileName)?.toResponseBody(jsonType)?.let { responseBody ->
                try {
                    Thread.sleep(MOCKED_HTTP_DELAY_MS)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                createResponse(
                    chain = chain,
                    responseBody = responseBody,
                    errorCode = mocking.errorCode,
                    header = mocking.header
                )
            }
        } ?: chain.proceed(request)
    }

    private fun getMockingForUrl(
        url: String
    ): Mocking? = mockings.find { mocking ->
        mocking.urlMatcher(url)
    }

    private fun createResponse(
        chain: Interceptor.Chain,
        responseBody: ResponseBody,
        errorCode: Int?,
        header: Pair<String, String>?
    ): Response = Response.Builder()
        .addHeader(
            "content-type",
            APPLICATION_JSON
        )
        .body(responseBody)
        .code(errorCode ?: HTTP_CODE_OK)
        .apply {
            if (header != null) {
                this.header(header.first, header.second)
            }
        }
        .message("Mocked Lexica response")
        .protocol(Protocol.HTTP_1_0)
        .request(chain.request())
        .build()

    private fun readFile(
        fileName: String
    ): String? = try {
        appContext.assets.open(fileName).let { inputStream ->
            val size = inputStream.available()
            val bytes = ByteArray(size)
            inputStream.read(bytes)
            inputStream.close()
            bytes
        }.let { bytes ->
            String(
                bytes = bytes,
                charset = Charset.forName(CHARSET_UTF_8)
            )
        }
    } catch (ex: IOException) {
        null
    }
}