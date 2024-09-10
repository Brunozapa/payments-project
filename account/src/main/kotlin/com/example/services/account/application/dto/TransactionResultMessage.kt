package com.example.services.account.application.dto

data class TransactionResultMessage(
    val transactionId: String,
    val status: TransactionStatus,
    val statusDetails: String? = null
)

enum class TransactionStatus {
    PENDING, REFUSED, COMPLETED, FAILED
}
