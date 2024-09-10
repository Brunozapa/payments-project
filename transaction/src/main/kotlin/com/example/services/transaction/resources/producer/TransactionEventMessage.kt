package com.example.services.transaction.resources.producer

import com.example.services.transaction.domain.entity.Transaction
import com.example.services.transaction.domain.entity.enums.TransactionType
import java.math.BigDecimal

data class TransactionEventMessage(
    val transactionId: String,
    val accountId: String,
    val transactionType: TransactionType,
    val amount: BigDecimal
) {
    companion object {
        fun of (transaction: Transaction) = TransactionEventMessage(
            transactionId = transaction.id,
            accountId = transaction.accountId,
            transactionType = transaction.type,
            amount = transaction.amount
        )
    }
}
