package com.example.services.account.application.dto

import java.math.BigDecimal

data class TransactionEventMessage(
    val transactionId: String,
    val accountId: String,
    val transactionType: TransactionType,
    val amount: BigDecimal
)

enum class TransactionType {
    PAYMENT, RECEIPT
}