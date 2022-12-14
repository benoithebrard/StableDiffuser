package com.benoithebrard.stablediffuser.network.lexica

import okhttp3.Headers
import java.io.IOException

sealed class LexicaError(
    message: String? = null,
    cause: Throwable? = null
) : IOException(message, cause) {

    class Unknown(
        cause: Throwable? = null,
        message: String? = null
    ) : LexicaError(message, cause)

    class Response(
        val statusCode: Int,
        val headers: Headers
    ) : LexicaError()

    object MissingBody : LexicaError()
}