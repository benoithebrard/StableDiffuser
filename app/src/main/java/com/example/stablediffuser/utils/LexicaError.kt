package com.example.stablediffuser.utils

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
        val errorCode: Int
    ) : LexicaError()

    object MissingBody : LexicaError()
}