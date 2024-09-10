package com.example.services.transaction.application.consumer

import com.example.services.transaction.domain.entity.enums.TransactionStatus

data class TransactionResultMessage(
    val transactionId: String,
    val status: TransactionStatus,
    val statusDetails: String?
)
