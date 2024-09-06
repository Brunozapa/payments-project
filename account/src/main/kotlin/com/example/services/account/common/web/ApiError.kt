package com.example.services.account.common.web

import com.example.services.account.domain.exception.BusinessException

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
