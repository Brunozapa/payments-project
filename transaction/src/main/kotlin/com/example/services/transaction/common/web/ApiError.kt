package com.example.services.transaction.common.web

import com.example.services.transaction.domain.exception.BusinessException
import org.springframework.web.client.HttpClientErrorException.NotFound

data class ApiError(
    val type: String,
    val message: String? = "Unknown Error"
) {
    companion object {
        fun of (exception: Exception) = ApiError(
            type = "UNKNOWN",
            message = exception.message
        )
        fun of(exception: BusinessException) = ApiError(
            type = exception.type,
            message = exception.msg
        )
    }
}