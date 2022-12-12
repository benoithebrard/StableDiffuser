package com.example.stablediffuser.config

import com.example.stablediffuser.config.Configuration.provideAppContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.nio.charset.Charset

private const val HTTP_OK = 200

private const val MOCKED_HTTP_DELAY_MS = 300L

private const val APPLICATION_JSON = "application/json"

private const val CHARSET_UTF8 = "UTF-8"

class MockingInterceptor(
    private val mockings: List<Mocking>
) : Interceptor {

    data class Mocking(
        val urlMatcher: (String) -> Boolean,
        val jsonFileName: String
    )

    private val jsonType: MediaType? by lazy {
        APPLICATION_JSON.toMediaTypeOrNull()
    }

    override fun intercept(
        chain: Interceptor.Chain
    ): Response {
        val request = chain.request()
        val requestUrl = request.url.toUrl().toString()

        val responseBody = getMockingFileNameForUrl(
            url = requestUrl
        )?.let { fileName ->
            readFile(fileName)
        }?.toResponseBody(jsonType)

        return if (responseBody != null) {
            try {
                Thread.sleep(MOCKED_HTTP_DELAY_MS)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            createResponse(chain, responseBody)
        } else {
            chain.proceed(request)
        }
    }

    private fun getMockingFileNameForUrl(
        url: String
    ): String? = mockings.find { mocking ->
        mocking.urlMatcher(url)
    }?.jsonFileName

    private fun createResponse(
        chain: Interceptor.Chain,
        responseBody: ResponseBody
    ): Response = Response.Builder()
        .addHeader(
            "content-type",
            APPLICATION_JSON
        )
        .body(responseBody)
        .code(HTTP_OK)
        .message("Mocked Lexica response")
        .protocol(Protocol.HTTP_1_0)
        .request(chain.request())
        .build()

    private fun readFile(
        fileName: String
    ): String? = try {
        provideAppContext().assets.open(fileName).let { inputStream ->
            val size = inputStream.available()
            val bytes = ByteArray(size)
            inputStream.read(bytes)
            inputStream.close()
            bytes
        }.let { bytes ->
            String(
                bytes = bytes,
                charset = Charset.forName(CHARSET_UTF8)
            )
        }
    } catch (ex: IOException) {
        null
    }
}