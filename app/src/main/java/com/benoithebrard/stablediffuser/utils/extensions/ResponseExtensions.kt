package com.benoithebrard.stablediffuser.utils.extensions

import com.benoithebrard.stablediffuser.network.lexica.LexicaError
import retrofit2.Response

internal fun <T> Response<T>.toResult(): Result<T> = if (isSuccessful) {
    body()?.let { value ->
        Result.success(value)
    } ?: Result.failure(LexicaError.MissingBody)
} else {
    Result.failure(
        LexicaError.Response(
            statusCode = code(),
            headers = headers()
        )
    )
}