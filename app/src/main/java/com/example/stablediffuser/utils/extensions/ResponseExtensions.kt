package com.example.stablediffuser.utils.extensions

import com.example.stablediffuser.utils.LexicaError
import retrofit2.Response

internal fun <T> Response<T>.toResult(): Result<T> = if (isSuccessful) {
    body()?.let { value ->
        Result.success(value)
    } ?: Result.failure(LexicaError.MissingBody)
} else {
    Result.failure(LexicaError.Response(code()))
}