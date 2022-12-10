package com.example.stablediffuser.config

import com.example.stablediffuser.config.Configuration.provideApplicationContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.nio.charset.Charset

private const val HTTP_OK = 200

private const val APPLICATION_JSON = "application/json"

class MockingInterceptor(
    private val mockings: List<Mocking>
) : Interceptor {

    data class Mocking(
        val requestUrlMatcher: (String) -> Boolean,
        val responseJsonName: String
    )

    private val jsonType: MediaType? by lazy {
        APPLICATION_JSON.toMediaTypeOrNull()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestUrl = request.url.toUrl().toString()

        val responseBody = mockings.find { mocking ->
            mocking.requestUrlMatcher(requestUrl)
        }?.responseJsonName?.let { fileName ->
            readFile(fileName)
        }?.toResponseBody(jsonType)

        return if (responseBody != null) {
            try {
                Thread.sleep(100L)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            createResponse(
                chain,
                responseBody
            )
        } else {
            chain.proceed(request)
        }
    }

    private fun createResponse(
        chain: Interceptor.Chain,
        responseBody: ResponseBody?
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
        val inputStream = provideApplicationContext().assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)

        inputStream.read(buffer)
        inputStream.close()

        String(buffer, Charset.forName("UTF-8"))
    } catch (ex: IOException) {
        null
    }
}