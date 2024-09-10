package com.example.services.transaction.application.web.response

import com.example.services.transaction.domain.entity.Transaction
import com.example.services.transaction.domain.entity.enums.TransactionStatus

data class TransactionResponse(
    val id: String,
    val status: TransactionStatus,
    val statusDetails: String? = null
) {
    companion object {
        fun of(transaction: Transaction) = TransactionResponse(
            id = transaction.id,
            status = transaction.status,
            transaction.statusDetails
        )
    }
}
