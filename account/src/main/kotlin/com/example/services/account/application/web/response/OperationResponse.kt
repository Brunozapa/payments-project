package com.example.services.account.application.web.response

data class OperationResponse(
    val status: String,
    val message: String? = null
)

enum class OperationStatus(val response: String) {
    COMPLETED("EFETUADO"),
    REFUSED("RECUSADO ")
}