package com.example.services.transaction.resources.repository.entity

import com.example.services.transaction.domain.entity.Transaction
import com.example.services.transaction.domain.entity.enums.TransactionStatus
import com.example.services.transaction.domain.entity.enums.TransactionType
import de.huxhorn.sulky.ulid.ULID
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime

@Document(collection = "transactions")
data class TransactionEntity(
    val id: String,
    val accountId: String,
    val amount: BigDecimal,
    val status: TransactionStatus,
    val type: TransactionType,
    val transactionDate: LocalDateTime,
    val payerName: String?,
    val receiverName: String?,
    val statusDetails: String?
) {

    fun toDomain() = Transaction.of(
        id = id,
        accountId = accountId,
        amount = amount,
        status = status,
        type = type,
        transactionDate = transactionDate,
        payerName = payerName,
        receiverName = receiverName,
        statusDetails = statusDetails
    )

    companion object {
        fun of(transaction: Transaction) = TransactionEntity(
            id = transaction.id,
            accountId = transaction.accountId,
            amount = transaction.amount,
            status = transaction.status,
            type = transaction.type,
            transactionDate = transaction.transactionDate,
            payerName = transaction.payerName,
            receiverName = transaction.receiverName,
            statusDetails = transaction.statusDetails
        )
    }
}
